package com.example.thebagofholding

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.eligustilo.Hermez.Hermez
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList

data class ArmorItemData(
        val armorImage: Int,
        val armorName: String,
        val armorEffectsOne: String,
        val armorUUID: UUID
)

data class CharacterInformation(
        val characterName: String,
        val characterArmorItemsList: ArrayList<ArmorItemData>,
        val characterWeaponItemsList: ArrayList<WeaponItemData>,
        val characterConsumablesItemsList: ArrayList<ConsumablesItemData>,
        val characterMiscellaneousItemList: ArrayList<MiscellaneousItemData>,
        var characterPurseData: CharacterPurseData,
        val characterUUID: UUID
)

data class OtherPlayerCharacterInformation(
        val otherPlayerCharacterName : String,
        val otherPlayerCharacterUUID : UUID,
        val otherPlayerDeviceName : String
)

data class ConsumablesItemData(
        val consumablesImage: Int,
        val consumablesName: String,
        val consumablesEffectsOne: String,
        val consumablesUUID: UUID
)

data class MiscellaneousItemData(
        val miscImage: Int,
        val miscName: String,
        val miscEffectsOne: String,
        val miscUUID: UUID
)

data class CharacterPurseData(
        var bronze: String,
        var silver: String,
        var gold: String,
        var truesilver: String
)

data class WeaponItemData(
        val image: Int,
        val name: String,
        val weaponEffectOne: String,
        val weaponUUID: UUID
)

//todo good place to make bargain/transaction class

object DataMaster: Hermez.HermezDataInterface {
    private val tag = "DataMaster"
    private const val DATA_MASTER_KEY = "data_master_key"
    private const val CHARACTER_HASHTABLE_KEY = "character_hashtable_key"
    private const val CURRENT_CHARACTER_INFORMATION_KEY = "current_character_information_key"
    private var characterArray = ArrayList<CharacterInformation>()
    private var characterHashtable = Hashtable<String, String>()
    private var otherPlayersArray = ArrayList<OtherPlayerCharacterInformation>()
    private var hermezDevicesConnected = ArrayList<Hermez.HermezDevice>()
    private lateinit var hermez : Hermez //TODO how do i fisx
    private val phoneName = Build.MODEL
    var objectToNotify : DataMasterInterface? = null
    private lateinit var applicationContext : Context


    interface DataMasterInterface {
        fun giveCharacterInfo(characterInfo: CharacterInformation)
        fun giveAllCharactersInfo(characterInfoArray: ArrayList<CharacterInformation>)
        fun giveFriendsList(friendsList : ArrayList<OtherPlayerCharacterInformation>)
    }

    fun initWith (applicationContext: Context){
        this.applicationContext = applicationContext
        //TODO I would like to initialize heremz here but memory leak
        hermez = Hermez(applicationContext, "_bag_of_holding._tcp")
        hermez.setDeviceName(phoneName)
        hermez.findAvailableDevices()
        hermez.initWithDelegate(this)
    }

    //CHARACTERS
    fun saveCharacterInformation(character: CharacterInformation){
        //make a file cabinet with DATA_MASTER_KEY
        //take characterInformation Data Class and turn it into JSON
        //put that JSON into the characterHashtable with the character name as key
        //take that hashtable turn it into JSON
        //store that JSON in a file in the DATA_MASTER_KEY cabinet with key CHARACTER_HASHTABLE_KEY
        //store characterName with CURRENT_CHARACTER_INFORMATION_KEY so we can use that name to run through the entire hashmap to grab the current character.
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Character to be saved is: $character")
        val characterInformationAsJSON = Gson().toJson(character)
        val characterName = character.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        characterArray.add(character)
        objectToNotify?.giveAllCharactersInfo(characterArray)
    }

    fun changeCharacter(characterName: String){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        Log.d(tag, "Current character has been changed to $characterName")
    }

    fun deleteCharacter(character : CharacterInformation){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Character to be deleted is: $character")
        characterHashtable.remove(character.characterName)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON) //todo this might make it so there is no currentCharacter and no message alerting the user of that fact.
        editor?.apply()
        characterArray.remove(character)
        objectToNotify?.giveAllCharactersInfo(characterArray)
    }

    fun retrieveAllCharactersInformation() : ArrayList<CharacterInformation>{
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
            if(sharedPrefs.contains(CHARACTER_HASHTABLE_KEY)){//if the file cabinet has a file called characters
                val savedCharacterInformationHashtableJSON = sharedPrefs.getString(CHARACTER_HASHTABLE_KEY, null) //get the characters file
                if (savedCharacterInformationHashtableJSON != null){ //if that file isn't blank
                    characterHashtable = Gson().fromJson(savedCharacterInformationHashtableJSON, Hashtable<String, String>()::class.java) //turn the file from JSON into a hashtable.
                    characterArray.clear()
                    for (item in characterHashtable){//get all keys of all existing character
                        val characterAsJSON = item.value//for key get character Value
                        val characterAsCharacterObject = Gson().fromJson(characterAsJSON, CharacterInformation::class.java)//Got character object.
                        characterArray.add(characterAsCharacterObject)//this will be an array of one?
                        Log.d(tag, "Characters to be returned is $characterAsCharacterObject")
                        objectToNotify?.giveAllCharactersInfo(characterArray)
                    }
                    return characterArray
                }
            }
        return characterArray
    }

    fun retrieveCharacterInformation() : CharacterInformation?{
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        if(sharedPrefs.contains(CHARACTER_HASHTABLE_KEY)){//if the file cabinet has a file called characters
            val savedCharacterInformationHashtableJSON = sharedPrefs.getString(CHARACTER_HASHTABLE_KEY, null) //get the characters file
            if (savedCharacterInformationHashtableJSON != null){ //if that file isn't blank
                characterHashtable = Gson().fromJson(savedCharacterInformationHashtableJSON, Hashtable<String, String>()::class.java) //turn the file from JSON into a hashtable.
                if (sharedPrefs.contains(CURRENT_CHARACTER_INFORMATION_KEY)){ //if we have a current character
                    val currentCharacterName = sharedPrefs.getString(CURRENT_CHARACTER_INFORMATION_KEY, null) //We save characters by name so get name
                    val currentCharacterJSON = characterHashtable[currentCharacterName] //give the character name to the hashtable to get back the characterInformation object as a JSON blob.
                    if (currentCharacterJSON != null){ //means that the character Data Object exists as JSON
                        val character = Gson().fromJson(currentCharacterJSON, CharacterInformation::class.java) //turn into character object
                        Log.d(tag, "Character to be retrieved is $character")
                        objectToNotify?.giveCharacterInfo(character)
                        return character
                    }
                }
            }
        }
        return null
    }


    //ITEMS

    //SAVE
    fun saveItemArmor(characterOwner: CharacterInformation, armorItem: ArmorItemData){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be saved is: $armorItem character to save to is $characterOwner")
        characterOwner.characterArmorItemsList.add(armorItem)
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun saveItemWeapon(characterOwner: CharacterInformation, weaponItem: WeaponItemData){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be saved is: $weaponItem character to save to is $characterOwner")
        characterOwner.characterWeaponItemsList.add(weaponItem)
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun saveItemConsumable(characterOwner: CharacterInformation, consumableItem: ConsumablesItemData){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be saved is: $consumableItem character to save to is $characterOwner")
        characterOwner.characterConsumablesItemsList.add(consumableItem)
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun saveItemMiscellaneous(characterOwner: CharacterInformation, miscellaneousItem: MiscellaneousItemData){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be saved is: $miscellaneousItem character to save to is $characterOwner")
        characterOwner.characterMiscellaneousItemList.add(miscellaneousItem)
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    //DELETE
    fun deleteItemArmor(characterOwner: CharacterInformation, armorItem: ArmorItemData){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be removed is: $armorItem character to remove from is $characterOwner")
        characterOwner.characterArmorItemsList.remove(armorItem)
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun deleteItemWeapon(characterOwner: CharacterInformation, weaponItem: WeaponItemData){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be removed is: $weaponItem character to remove from is $characterOwner")
        characterOwner.characterWeaponItemsList.remove(weaponItem)
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun deleteItemConsumable(characterOwner: CharacterInformation, consumableItem: ConsumablesItemData){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be removed is: $consumableItem character to remove from is $characterOwner")
        characterOwner.characterConsumablesItemsList.remove(consumableItem)
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun deleteItemMiscellaneous(characterOwner: CharacterInformation, miscellaneousItem: MiscellaneousItemData){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be removed is: $miscellaneousItem character to remove from is $characterOwner")
        characterOwner.characterMiscellaneousItemList.remove(miscellaneousItem)
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    //PURSE
    fun saveMoney (characterOwner: CharacterInformation, newCharacterPurse: CharacterPurseData){
        val sharedPrefs = applicationContext.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "purse to be saved is: $newCharacterPurse character to save to is $characterOwner")
        characterOwner.characterPurseData = newCharacterPurse
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable[characterName] = characterInformationAsJSON
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }


//    TRANSFER via Hermez
    fun transferItemArmor(newCharacterOwner: OtherPlayerCharacterInformation, armorItem: ArmorItemData){
        val armorToTransfer = Gson().toJson(armorItem)
        val hermezDeviceArray = ArrayList<Hermez.HermezDevice>()
        hermezDeviceArray.add(Hermez.HermezDevice(newCharacterOwner.otherPlayerDeviceName))
        hermez.sendMessageToDevices("TRANSFER", armorToTransfer, "A_T ${armorItem.armorUUID}", hermezDeviceArray)
        Log.d(tag, "Hermez was called to send item: $armorItem to ${newCharacterOwner.otherPlayerCharacterName} at device ${newCharacterOwner.otherPlayerDeviceName}")
    }

    fun transferItemWeapon(newCharacterOwner: OtherPlayerCharacterInformation, weaponItem: WeaponItemData){
        val weaponToTransfer = Gson().toJson(weaponItem)
        val hermezDeviceArray = ArrayList<Hermez.HermezDevice>()
        hermezDeviceArray.add(Hermez.HermezDevice(newCharacterOwner.otherPlayerDeviceName))
        hermez.sendMessageToDevices("TRANSFER", weaponToTransfer, "W_T ${weaponItem.weaponUUID}", hermezDeviceArray)
        Log.d(tag, "Hermez was called to send item: $weaponItem to ${newCharacterOwner.otherPlayerCharacterName} at device ${newCharacterOwner.otherPlayerDeviceName}")
    }

    fun transferItemConsumable(newCharacterOwner: OtherPlayerCharacterInformation, consumableItem: ConsumablesItemData){
        val consumableToTransfer = Gson().toJson(consumableItem)
        val hermezDeviceArray = ArrayList<Hermez.HermezDevice>()
        hermezDeviceArray.add(Hermez.HermezDevice(newCharacterOwner.otherPlayerDeviceName))
        hermez.sendMessageToDevices("TRANSFER", consumableToTransfer, "C_T ${consumableItem.consumablesUUID}", hermezDeviceArray)
        Log.d(tag, "Hermez was called to send item: $consumableItem to ${newCharacterOwner.otherPlayerCharacterName} at device ${newCharacterOwner.otherPlayerDeviceName}")
    }

    fun transferItemMiscellaneous(newCharacterOwner: OtherPlayerCharacterInformation, miscellaneousItem: MiscellaneousItemData){
        val miscToTransfer = Gson().toJson(miscellaneousItem)
        val hermezDeviceArray = ArrayList<Hermez.HermezDevice>()
        hermezDeviceArray.add(Hermez.HermezDevice(newCharacterOwner.otherPlayerDeviceName))
        hermez.sendMessageToDevices("TRANSFER", miscToTransfer, "M_T ${miscellaneousItem.miscUUID}", hermezDeviceArray)
        Log.d(tag, "Hermez was called to send item: $miscToTransfer to ${newCharacterOwner.otherPlayerCharacterName} at device ${newCharacterOwner.otherPlayerDeviceName}")
    }

    //HERMEZ SETUP
    fun findOtherPlayers() : ArrayList<OtherPlayerCharacterInformation>{
        Log.d(tag, "findOtherPlayers array = $otherPlayersArray")
        return otherPlayersArray
    }

    fun resetHermez(){//todo I may want to add a distinction between these two. Reset Registration and Reset Discovery. Hermez may want to make same distinction.
        otherPlayersArray.clear()
        hermez.resetDiscovery()
    }

    fun sendWhisperToPlayer(sendingPlayer: CharacterInformation, playerToSendWhisperTo : OtherPlayerCharacterInformation, whisper : String){
        val hermezDeviceArray = ArrayList<Hermez.HermezDevice>()
        hermezDeviceArray.add(Hermez.HermezDevice(playerToSendWhisperTo.otherPlayerDeviceName))
        hermez.sendMessageToDevices("WHISPER", whisper, sendingPlayer.characterName, hermezDeviceArray)
        Log.d(tag, "Whisper $whisper was sent to ${playerToSendWhisperTo.otherPlayerCharacterName} by ${sendingPlayer.characterName}")
    }


    //Hermez Interface
    override fun serviceStarted(serviceType: String, serviceName: String) {
        Log.d(tag, "serviceStarted called")
    }

    override fun serviceStopped(serviceType: String, serviceName: String) {
        Log.d(tag, "serviceStopped called")
    }

    override fun messageReceived(hermezMessage: Hermez.HermezMessage) {
        Log.d("messageReceived", "messageReceived called: = $hermezMessage")
        if (hermezDevicesConnected.contains(hermezMessage.sendingDevice)){
            Log.d("messageReceived", "the hermezDevicesConnected is $hermezDevicesConnected")
            //we have already connected to the other person at least once. We need to try and send them a message and confirm we are still connected. Else we need to remove them and then rediscover
        }else{
            //someone sees me but I don't see them. I need to discover them.
            hermezDevicesConnected.add(hermezMessage.sendingDevice)
            Log.d("messageReceived", "the hermezDevicesConnected is $hermezDevicesConnected")
            val currentCharacter = retrieveCharacterInformation()
            if (currentCharacter != null){//my own character exists and I can pass them back to whoever asked for it.
                val mpCharacter = OtherPlayerCharacterInformation(currentCharacter.characterName, currentCharacter.characterUUID, phoneName)
                val mpCharacterAsJson = Gson().toJson(mpCharacter)
                val senderArrayList = ArrayList<Hermez.HermezDevice>()//todo my own phone is sending me a message. this is likely a hermez problem. we should fix it?
                senderArrayList.add(hermezMessage.sendingDevice)
                hermez.sendMessageToDevices("Give me character details", mpCharacterAsJson, "002", senderArrayList) //our own device is listed here?
                Log.d(tag, "characterAsJson = $mpCharacterAsJson")
                Log.d(tag, "arrayList who sent me message = $senderArrayList")
            }
        }

        when (hermezMessage.message) {
            "Give me character details" -> {//todo get a better name for this string/key ENUM?? //also what do i do if I don't have a character? Do i need to reset? or only register my own service once I have a character?
                if (hermezMessage.sendingDevice.name == phoneName){
                    //it is our phone so ignore. todo maybe we should change hermez so this doesn't happen.
                }else{//it is someone else's phone
                    val otherPlayerCharacterInformation = Gson().fromJson(hermezMessage.json, OtherPlayerCharacterInformation::class.java) //get their character
                    if (otherPlayersArray.contains(otherPlayerCharacterInformation)){
                        //otherPlayer array already has it. aka do nothing? Or toast?
                        objectToNotify?.giveFriendsList(otherPlayersArray)
                    }else{
                        otherPlayersArray.add(otherPlayerCharacterInformation)
                        objectToNotify?.giveFriendsList(otherPlayersArray)
                        val currentCharacter = retrieveCharacterInformation()
                        if (currentCharacter != null){//my own character exists and I can pass them back to whoever asked for it.
                            val mpCharacter = OtherPlayerCharacterInformation(currentCharacter.characterName, currentCharacter.characterUUID, phoneName)
                            val mpCharacterAsJson = Gson().toJson(mpCharacter)
                            val senderArrayList = ArrayList<Hermez.HermezDevice>()
                            senderArrayList.add(hermezMessage.sendingDevice)
                            hermez.sendMessageToDevices("Give me character details", mpCharacterAsJson, "002", senderArrayList) //our own device is listed here?
                            Log.d(tag, "characterAsJson = $mpCharacterAsJson")
                            Log.d(tag, "arrayList who sent me message = $senderArrayList")
                        }
                    }
                }
            }

            "TRANSFER" ->{
                if (hermezMessage.sendingDevice.name == phoneName){ //it is our phone so ignore.
                }else{//it is someone else's phone
                    if (hermezMessage.messageID.contains("W_T")){//make this prettier and change when statement syntax
                        Log.d(tag, "W_T called")
                        val receivedItemWeaponData = Gson().fromJson(hermezMessage.json, WeaponItemData::class.java)
                        val newItemOwner = retrieveCharacterInformation()
                        if (newItemOwner != null) {
                            saveItemWeapon(newItemOwner, receivedItemWeaponData)
                            val arrayListToReturnMessageTo = ArrayList<Hermez.HermezDevice>()
                            arrayListToReturnMessageTo.add(hermezMessage.sendingDevice)
                            hermez.sendMessageToDevices("TRANSFER_SUCCESS", hermezMessage.json, hermezMessage.messageID, arrayListToReturnMessageTo)
                        }
                    }
                    if (hermezMessage.messageID.contains("A_T")){//make this prettier and change when statement syntax
                        Log.d(tag, "A_T called")
                        val receivedItemArmorData = Gson().fromJson(hermezMessage.json, ArmorItemData::class.java)
                        val newItemOwner = retrieveCharacterInformation()
                        if (newItemOwner != null) {
                            saveItemArmor(newItemOwner, receivedItemArmorData)
                            val arrayListToReturnMessageTo = ArrayList<Hermez.HermezDevice>()
                            arrayListToReturnMessageTo.add(hermezMessage.sendingDevice)
                            hermez.sendMessageToDevices("TRANSFER_SUCCESS", hermezMessage.json, hermezMessage.messageID, arrayListToReturnMessageTo)
                        }
                    }
                    if (hermezMessage.messageID.contains("C_T")){//make this prettier and change when statement syntax
                        Log.d(tag, "C_T called")
                        val receivedItemConsumableData = Gson().fromJson(hermezMessage.json, ConsumablesItemData::class.java)
                        val newItemOwner = retrieveCharacterInformation()
                        if (newItemOwner != null) {
                            saveItemConsumable(newItemOwner, receivedItemConsumableData)
                            val arrayListToReturnMessageTo = ArrayList<Hermez.HermezDevice>()
                            arrayListToReturnMessageTo.add(hermezMessage.sendingDevice)
                            hermez.sendMessageToDevices("TRANSFER_SUCCESS", hermezMessage.json, hermezMessage.messageID, arrayListToReturnMessageTo)
                        }
                    }
                    if (hermezMessage.messageID.contains("M_T")){//make this prettier and change when statement syntax
                        Log.d(tag, "M_T called")
                        val receivedItemMiscData = Gson().fromJson(hermezMessage.json, MiscellaneousItemData::class.java)
                        val newItemOwner = retrieveCharacterInformation()
                        if (newItemOwner != null) {
                            saveItemMiscellaneous(newItemOwner, receivedItemMiscData)
                            val arrayListToReturnMessageTo = ArrayList<Hermez.HermezDevice>()
                            arrayListToReturnMessageTo.add(hermezMessage.sendingDevice)
                            hermez.sendMessageToDevices("TRANSFER_SUCCESS", hermezMessage.json, hermezMessage.messageID, arrayListToReturnMessageTo)
                        }
                    }
                }
            }

            "TRANSFER_SUCCESS" ->{
                if (hermezMessage.messageID.contains("W_T")){//make this prettier and change when statement syntax
                    Log.d(tag, "W_T success called")
                    val receivedItemWeaponData = Gson().fromJson(hermezMessage.json, WeaponItemData::class.java)
                    val characterToRemoveItem = retrieveCharacterInformation()
                    if (characterToRemoveItem != null) {
                        deleteItemWeapon(characterToRemoveItem, receivedItemWeaponData)
                        objectToNotify!!.giveCharacterInfo(characterToRemoveItem)
                        Log.d(tag, "Item deleted = $receivedItemWeaponData and character removed from = $characterToRemoveItem")
                    }
                }
                if (hermezMessage.messageID.contains("A_T")){//make this prettier and change when statement syntax
                    Log.d(tag, "A_T success called")
                    val receivedItemArmorData = Gson().fromJson(hermezMessage.json, ArmorItemData::class.java)
                    val characterToRemoveItem = retrieveCharacterInformation()
                    if (characterToRemoveItem != null) {
                        deleteItemArmor(characterToRemoveItem, receivedItemArmorData)
                        objectToNotify!!.giveCharacterInfo(characterToRemoveItem)
                        Log.d(tag, "Item deleted = $receivedItemArmorData and character removed from = $characterToRemoveItem")
                    }
                }
                if (hermezMessage.messageID.contains("C_T")){//make this prettier and change when statement syntax
                    Log.d(tag, "C_T success called")
                    val receivedItemConsumableData = Gson().fromJson(hermezMessage.json, ConsumablesItemData::class.java)
                    val characterToRemoveItem = retrieveCharacterInformation()
                    if (characterToRemoveItem != null) {
                        deleteItemConsumable(characterToRemoveItem, receivedItemConsumableData)
                        objectToNotify!!.giveCharacterInfo(characterToRemoveItem)
                        Log.d(tag, "Item deleted = $receivedItemConsumableData and character removed from = $characterToRemoveItem")
                    }
                }
                if (hermezMessage.messageID.contains("M_T")){//make this prettier and change when statement syntax
                    Log.d(tag, "M_T success called")
                    val receivedItemMiscData = Gson().fromJson(hermezMessage.json, MiscellaneousItemData::class.java)
                    val characterToRemoveItem = retrieveCharacterInformation()
                    if (characterToRemoveItem != null) {
                        deleteItemMiscellaneous(characterToRemoveItem, receivedItemMiscData)
                        objectToNotify!!.giveCharacterInfo(characterToRemoveItem)
                        Log.d(tag, "Item deleted = $receivedItemMiscData and character removed from = $characterToRemoveItem")
                    }
                }
            }
            "TRANSFER_FAILURE" ->{
                print("x == 2")
            }
            "WHISPER" ->{
                Log.d(tag, "Whisper recieved called")
                val messageSender = hermezMessage.messageID
                val whisperSent = hermezMessage.json
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(applicationContext, "$messageSender just whispered: $whisperSent", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun serviceFailed(serviceType: String, serviceName: String?, error: Hermez.HermezError) {
        Log.d(tag, "serviceFailed called")
        val hermezDeviceLost = serviceName?.let { Hermez.HermezDevice(it) }
        hermezDevicesConnected.remove(hermezDeviceLost)
    }

    override fun messageCannotBeSentToDevices(hermezMessage: Hermez.HermezMessage, error: Hermez.HermezError) {
        Log.d(tag, "messageCannotBeSentToDevices called")
    }

    override fun devicesFound(deviceList: ArrayList<Hermez.HermezDevice>) {
        Log.d(tag, "devicesFound called. List = $deviceList")
//        otherPlayersArray.clear()
        for (item in deviceList){
            if (item.name == phoneName){
                //do not get character data. Its my phone
                //todo what if they have the same phoneName/type? UUID?
            }else{//not my device so get their character data.
//                otherPlayersArray.add(item.name)
                val arrayForSingleDevice = ArrayList<Hermez.HermezDevice>()
                arrayForSingleDevice.add(Hermez.HermezDevice(item.name))
                val currentCharacter = retrieveCharacterInformation()
                if (currentCharacter != null){//our character exists
                    val mpCharacter = OtherPlayerCharacterInformation(currentCharacter.characterName, currentCharacter.characterUUID, phoneName)
                    val mpCharacterAsJson = Gson().toJson(mpCharacter)
                    hermez.sendMessageToDevices("Give me character details", mpCharacterAsJson, "001", arrayForSingleDevice) //our own device is listed here?
                }else{//does not exist so send our device name instead

                }
            }
        }
//
//        /*
//        * steps
//        * 1) get names of devices on networks
//        * 2) ask all names of devices on network to send me a message with their character details as a json blob
//        * 3) get their json blob and turn it into a character object here.
//        * 4) return new characterArray to UI
//        * */
    }

    override fun resolveFailed(serviceType: String, serviceName: String, error: Hermez.HermezError) {
        Log.d(tag, "resolveFailed called $serviceType $serviceName $error")
        val hermezDeviceLost = serviceName.let { Hermez.HermezDevice(it) }
        hermezDevicesConnected.remove(hermezDeviceLost)//todo is this right? i could be removing things that don't exist in the array?
    }
}