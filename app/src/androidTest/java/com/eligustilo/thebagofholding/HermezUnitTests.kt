package com.eligustilo.thebagofholding

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HermezUnitTests : Hermez.HermezDataInterface {
    var arrayListOfDevices = ArrayList<Hermez.HermezDevice>()
    val latch = CountDownLatch(1)
    var messageRecieved : Hermez.HermezMessage? = null

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.thebagofholding", appContext.packageName)
    }


    @Test
    fun testFindDevice() {
        val tag = "testFindDevice"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val hermez = Hermez(appContext, "_bag_of_holding._tcp")
        hermez.initWithDelegate(this)
        hermez.setDeviceName(Build.MODEL)
        checkForDevice(hermez)
//        var i = 0
//        while (i < 10){
//            arrayListOfDevices.clear()
//            checkForDevice(hermez)
//            i++
//        }
    }

    @Test
    fun testSendMessage() {
        // Context of the app under test.
        val tag = "testSendMessage"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val hermez = Hermez(appContext, "_bag_of_holding._tcp")
        hermez.initWithDelegate(this)
        hermez.setDeviceName(Build.MODEL)
        hermez.findAvailableDevices()
        Thread.sleep(3000)
        assertTrue("all devices found = $arrayListOfDevices", arrayListOfDevices.size > 1)
        hermez.sendMessageToDevices("unit_test","null","0001", arrayListOfDevices)
        Thread.sleep(3000)
        assertTrue("message = $messageRecieved", messageRecieved != null)
    }

    @Test
    fun testReceiveMessage() {
        val tag = "testReceiveMessage"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val hermez = Hermez(appContext, "_bag_of_holding._tcp")
        hermez.initWithDelegate(this)
        hermez.setDeviceName(Build.MODEL)
        hermez.findAvailableDevices()
        Thread.sleep(5000)
        assertTrue("all devices found = $arrayListOfDevices", arrayListOfDevices.size > 1)
        var i = 0
        while (i < 10){
            messageRecieved = null
            sendMessage(hermez)
            i++
        }
    }

    private fun sendMessage(hermez: Hermez){
        hermez.sendMessageToDevices("unit_test","null","0001", arrayListOfDevices)
        Thread.sleep(2000)
        assertTrue("message = $messageRecieved", messageRecieved != null)
        Thread.sleep(2000)
    }

    private fun checkForDevice(hermez: Hermez){
        hermez.findAvailableDevices()
        Thread.sleep(5000)
        assertTrue("all devices found = $arrayListOfDevices", arrayListOfDevices.size > 1)
        Thread.sleep(5000)
    }

    override fun serviceStarted(serviceType: String, serviceName: String) {
    }

    override fun serviceStopped(serviceType: String, serviceName: String) {
    }

    override fun messageReceived(hermezMessage: Hermez.HermezMessage) {
        messageRecieved = hermezMessage
    }

    override fun serviceFailed(serviceType: String, serviceName: String?, error: Hermez.HermezError) {
    }

    override fun messageCannotBeSentToDevices(hermezMessage: Hermez.HermezMessage, error: Hermez.HermezError) {
    }

    override fun devicesFound(deviceList: ArrayList<Hermez.HermezDevice>) {
        arrayListOfDevices = deviceList
    }

    override fun resolveFailed(serviceType: String, serviceName: String, error: Hermez.HermezError) {
    }
}