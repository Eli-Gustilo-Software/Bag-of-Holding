package com.example.thebagofholding

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdManager.DiscoveryListener
import android.net.nsd.NsdServiceInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.*
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

/*
* This Library is setup to transfer data from a 'Discoverer' to a 'Broadcaster'/'Registerer'
* It accomplishes this by utilizing ZeroConfig.
*
* API Usage: (Across Android and Swift devices)
* 1) Must instantiate the Library: Providing Context and a serviceType such as _myexampletype._tcp
* 2) In order to be successfully found you must call setDeviceName and provide the Library a unique deviceName. (Upon setting the name your phone will broadcast itself as serviceType with serviceName) (Note you can change serviceType)
* 3) Your app can call findDevices to obtain a ArrayList of devices on the local WiFi network utilizing your _myexampletype._tcp serviceType.
* 4) Your app may then send a Message Object to any unique deviceName sharing your unique serviceType. Example: mMyMessage = ("Name", "ParsableMessage", "UniqueMessageID", deviceNameToSendTo)
* 5) When you send a message your app 'discovers' all nearby devices with _myexampletype._tcp and then looks for deviceNameToSendTo and relays your message ASynchronously
* */


const val MESSAGE_TERMINATOR = "\r\n"
//This Class is split into two different inner classes Server and Client
class Hermez(context: Context, serviceType: String) {
    private val tag = "Hermez"
    private var mServiceType: String = serviceType
    private var mServiceName: String? = null
    private var mArrayOfDevicesFound: ArrayList<HermezDevice>? = ArrayList()
    private var mMutableLiveDataDevices : MutableLiveData<HermezDevice> = MutableLiveData()
    private var mContext = context
    private var mHashtable = Hashtable<String, Socket>()
    private var objectToNotify: HermezDataInterface? = null
    private var hermezService: HermezService? = null
    private var hermezBrowser: HermezBrowser? = null
    private val unknownZeroConfigDevice: HermezDevice = HermezDevice("Unknown Device")
    private val myDeviceName : HermezDevice = HermezDevice(Build.MODEL)

    init {
        hermezService = HermezService()
        hermezBrowser = HermezBrowser()
    }

    enum class HermezError {
        SERVICE_FAILED, MESSAGE_NOT_SENT, SERVICE_RESOLVE_FAILED
    }

    data class HermezDevice(
        val name: String,
    )

    data class HermezMessage(
        val message: String,
        val json: String,
        val messageID: String,
        val receivingDevice: HermezDevice,
        val sendingDevice: HermezDevice)


    // All devices on the network will create there own zero config service with (1) a common service type and (2) unique service names (for each device)
    interface HermezDataInterface {
        fun serviceStarted(serviceType: String, serviceName: String)
        fun serviceStopped(serviceType: String, serviceName: String)
        fun messageReceived(hermezMessage: HermezMessage) //todo mutable
        fun serviceFailed(serviceType: String, serviceName: String?, error: HermezError)
        fun messageCannotBeSentToDevices(hermezMessage: HermezMessage, error: HermezError)
        fun devicesFound(deviceList: ArrayList<HermezDevice>) //todo mutable
        fun devicesFoundMutable(deviceList : MutableLiveData<HermezDevice>)
        fun resolveFailed(serviceType: String, serviceName: String, error: HermezError)
    }

    fun setServiceName(serviceName: String) {
        // will be used to set service type, for example, "_<serviceName>._tcp" can be used to change it in case user wants multiple serviceTypes.
        mServiceType = serviceName
        //TODO how can I enforce a good convention for writing it? Requires Formatter and work
        if (mServiceName != null && mServiceType != null) {
            HermezService().registerService()
        } else {
            Log.e(tag, "This ZeroConfig Library requires a correct serviceType and a deviceName")
        }
    }

    fun setDeviceName(deviceName: String) {
        // will be used to set unique name of device
        mServiceName = deviceName
        if (mServiceName != null && mServiceType != null) {
            HermezService().registerService()
        } else {
            Log.e(tag, "This ZeroConfig Library requires a correct serviceType and a deviceName")
        }
    }

    fun findAvailableDevices() {
        hermezBrowser?.discoverService()
    }

    fun sendMessageToDevices(message: String, json: String, messageID: String, devices: ArrayList<HermezDevice>) {
        // will send message to devices (see interface on receiving message)
        // messageID is a unique identifier for that message
        for (device in devices){
            val zConfigDevice  = HermezDevice(device.name)
            val mMessage = HermezMessage(message, json, messageID, zConfigDevice, myDeviceName)
            Log.d(tag, "message= $mMessage")
            val deviceToTalkToKey = zConfigDevice.name
            val socketOfDeviceToTalkTo = mHashtable[deviceToTalkToKey]
            if (socketOfDeviceToTalkTo != null) {
                val runnable = hermezBrowser?.ClientWriter(mMessage, socketOfDeviceToTalkTo)
                val writerThread = Thread(runnable)
                writerThread.start()
            }
        }
    }

    fun resetService(){
        //While hopefully unnecessary it may be desirable to be able to throw the whole process away and begin again.
        hermezBrowser?.resetDiscovery()
        hermezService?.resetRegistration()
    }


    fun initWithDelegate(delegate: HermezDataInterface? = null) {
        this.objectToNotify = delegate
    }

    //SERVER CODE (Registration)
    private inner class HermezService {
        private var localPortServer = 101
        private var nsdManagerServer: NsdManager? = null
        private var connectedClientsServer: MutableList<Socket> = CopyOnWriteArrayList()

        private fun initializeServerSocket() {
            // Initialize a server socket on the next available port.
            val serverSocket = ServerSocket(0).also { socket ->
                // Store the chosen port.
                localPortServer = socket.localPort
            }
            Thread {
                //serverSocket != null
                while (true) {
                    try {
                        Log.d(tag, "serverSocket set to accept on ${serverSocket.localPort}")
                        serverSocket.accept()?.let {
                            Log.d("ServerSocket", "accepted client")
                            Log.d(tag, "new IT Socket = ${it.port}")
                            // Hold on to the client socket
                            connectedClientsServer.add(it)

                            // Start reading messages
                            Thread(ServiceReader(it)).start()
                            //NOTE you can have broadcast a message back to the discoverer here. Would need to create a reader for Client.
                        }
                    } catch (e: SocketException) {
                        break
                    }
                }
            }.start()
        }


        fun registerService() {
            initializeServerSocket()
            val serviceInfo = NsdServiceInfo().apply {
                // The name is subject to change based on conflicts
                // with other services advertised on the same network.
                serviceName = mServiceName
                serviceType = mServiceType
                port = localPortServer
            }
            nsdManagerServer = (mContext.getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
                registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
            }
        }

        fun resetRegistration(){
            nsdManagerServer?.unregisterService(registrationListener)
            nsdManagerServer = null
            GlobalScope.launch { // launch new coroutine in background and continue
                delay(3000) // non-blocking delay for 1 second (default time unit is ms)
                registerService()
            }
        }

        private val registrationListener = object : NsdManager.RegistrationListener {
            override fun onServiceRegistered(NsdServiceInfo: NsdServiceInfo) {
                // Save the service name. Android may have changed it in order to
                // resolve a conflict, so update the name you initially requested
                // with the name Android actually used.
                val registrationListenerServiceName = NsdServiceInfo.serviceName
                val registrationListenerServicePort = NsdServiceInfo.port
                val type = NsdServiceInfo.serviceType
                objectToNotify?.serviceStarted(mServiceType, mServiceName!!)
                Log.d(tag, "It worked! Service is registered! Service name is $registrationListenerServiceName Service port is $registrationListenerServicePort and Service type is $type")
            }

            override fun onRegistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                // Registration failed! Put debugging code here to determine why.
                Log.e(tag, "Registration failed. ServiceInfo = $serviceInfo and error code = $errorCode")
            }

            override fun onServiceUnregistered(arg0: NsdServiceInfo) {
                // Service has been unregistered. This only happens when you call
                // NsdManager.unregisterService() and pass in this listener.
                Log.i(tag, "yourService: $arg0 has been unregistered.")
            }

            override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                // Unregistration failed. Put debugging code here to determine why.
                Log.e(tag, "Unregistration failed. ServiceInfo = $serviceInfo and error code = $errorCode")
            }
        }

        private inner class ServiceReader(private val client: Socket) : Runnable {
            private val tag = "ServiceReader"
            override fun run() {
                var line: String?
                val reader: BufferedReader

                try {
                    reader = BufferedReader(InputStreamReader(client.getInputStream()))
                    Log.w(tag, "BufferedReader got hit")
                } catch (e: IOException) {
                    Log.w(tag, "BufferedReader failed to initialize")
                    connectedClientsServer.remove(client)
                    return
                }

                while (true) {
                    try {
                        line = reader.readLine()
                        Log.d(tag, "line read is $line")
                        if (line == null) {
                            connectedClientsServer.remove(client)
                            break
                        } else {
                            val message = Gson().fromJson(line, HermezMessage::class.java)
                            Log.d(tag, "parsed message is $message")
                            objectToNotify?.messageReceived(message)
                        }
                    } catch (e: IOException) {
                        connectedClientsServer.remove(client)
                        break
                    }
                }
            }
        }
    }

    //CLIENT CODE
    private inner class HermezBrowser {
        private var nsdManagerClient: NsdManager? = null
        private var multicastLock: WifiManager.MulticastLock? = null
        private var discoverListenerInUse: Boolean = false

        fun discoverService() {
            //Multicast is required to see all devices but consumes power, we turn it off as soon as searching is done.
            multicastLock = (mContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).createMulticastLock(tag)
            multicastLock?.setReferenceCounted(true)
            multicastLock?.acquire()
            nsdManagerClient = mContext.getSystemService(Context.NSD_SERVICE) as NsdManager

            when (discoverListenerInUse) {
                false -> {
                    discoverListenerInUse = true
                    nsdManagerClient!!.discoverServices(mServiceType, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
                }
                true -> {
                    Log.e(tag, "Only need to search for devices once. The Library will give you an updated array list of all found devices with serviceType")
                }
            }
        }

        fun resetDiscovery(){
            nsdManagerClient?.stopServiceDiscovery(discoveryListener)
            nsdManagerClient = null
            mHashtable.clear()

            GlobalScope.launch { // launch new coroutine in background and continue
                delay(3000) // non-blocking delay for 1 second (default time unit is ms)
                discoverService()
            }
        }

        private val discoveryListener = object : DiscoveryListener {
            override fun onDiscoveryStarted(regType: String) {
                Log.d(tag, "Service discovery started. Attempting to find serviceType.")
            }

            override fun onServiceFound(service: NsdServiceInfo) {
                // A service was found! Do something with it.
                Log.d(tag, "Service discovery success $service found")
                nsdManagerClient?.resolveService(service, MyResolveListener())
                multicastLock?.release()
                multicastLock = null
            }

            override fun onServiceLost(service: NsdServiceInfo) {
                // When the network service is no longer available.
                // Internal bookkeeping code goes here.
                Log.e(tag, "service lost: ${service.serviceName}")
                multicastLock?.release()
                multicastLock = null
                objectToNotify?.serviceFailed(mServiceType, (service.serviceName), HermezError.SERVICE_FAILED)
                //resetDiscovery()
            }

            override fun onDiscoveryStopped(serviceType: String) {
                Log.i(tag, "Discovery stopped: $serviceType")
                multicastLock?.release()
                multicastLock = null
                objectToNotify?.serviceStopped(mServiceType, (unknownZeroConfigDevice.name))
            }

            override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
                Log.e(tag, "Discovery failed: Error code:$errorCode")
                multicastLock?.release()
                multicastLock = null
                objectToNotify?.serviceFailed(mServiceType, (unknownZeroConfigDevice.name), HermezError.SERVICE_FAILED)
            }

            override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
                Log.e(tag, "Discovery failed: Error code:$errorCode")
                multicastLock?.release()
                multicastLock = null
                objectToNotify?.serviceFailed(mServiceType, (unknownZeroConfigDevice.name), HermezError.SERVICE_FAILED)
            }
        }

        private inner class MyResolveListener : NsdManager.ResolveListener {
            private val TAG = "resolveListener"

            override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                // Called when the resolve fails. Use the error code to debug.
                Log.e(TAG, "Resolve failed: $errorCode")
                objectToNotify?.resolveFailed(mServiceType, serviceInfo.serviceName, HermezError.SERVICE_RESOLVE_FAILED)
            }

            override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
                Log.d(TAG, "Resolve Succeeded. $serviceInfo")
                try {
                    // Connect to the host
                    val localPortClient = Socket(serviceInfo.host, serviceInfo.port)

                    mHashtable.put(serviceInfo.serviceName, localPortClient)
                    Log.d(TAG, "mHashtable: $mHashtable")
                    mArrayOfDevicesFound?.clear() //todo make this mutable and add it to the MutableLiveData object.???
                    for (item in mHashtable){
                        mArrayOfDevicesFound?.add(HermezDevice(item.key))
                    }
                    if (objectToNotify != null && mArrayOfDevicesFound != null){
                        objectToNotify!!.devicesFound(mArrayOfDevicesFound!!)
                    }
                    //Log.d(tag, "mHashtable is filled with ${serviceInfo.serviceName} and $localPortClient")
                    //Log.d(tag, "connection state = ${localPortClient.isConnected}")
                    val newMessage = HermezMessage("NSDClientClass did connect to service name ${serviceInfo.serviceName}", "", "0", HermezDevice(serviceInfo.serviceName), myDeviceName)
                    val runnable = hermezBrowser?.ClientWriter(newMessage, localPortClient)
                    val writerThread = Thread(runnable)
                    writerThread.start()
                } catch (e: UnknownHostException) {
                    Log.e(TAG, "Unknown host. ${e.localizedMessage}")
                    objectToNotify?.resolveFailed(serviceInfo.serviceType, serviceInfo.serviceName, HermezError.SERVICE_RESOLVE_FAILED)
                }
            }
        }

        inner class ClientWriter(val hermezMessage: HermezMessage, val socket: Socket) : Runnable {
            private val tag = "ClientWriter"
            override fun run() {
                val writer: PrintWriter
                try {
                    writer = PrintWriter(socket.getOutputStream())
                    val jsonString = Gson().toJson(hermezMessage)
                    Log.d(tag, "jsonString is $jsonString")
                    writer.print(jsonString + MESSAGE_TERMINATOR)
                    writer.flush()
                    //writer.close()
                } catch (e: IOException) {
                    // If the writer fails to initialize there was an io problem, close your connection
                    socket.close()
                    objectToNotify?.messageCannotBeSentToDevices(hermezMessage, HermezError.MESSAGE_NOT_SENT)
                }
            }//runnable close when fun run() ends.
        }
    }
}