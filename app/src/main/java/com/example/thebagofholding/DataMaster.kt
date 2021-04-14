package com.example.thebagofholding

import android.app.Application
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.MutableLiveData
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
        //TODO do i want these to be nullable? I could give the rando things that need it empty arrays? which is better?
        val characterArmorItemsList: ArrayList<ArmorItemData>,
        val characterWeaponItemsList: ArrayList<WeaponItemData>,
        val characterConsumablesItemsList: ArrayList<ConsumablesItemData>,
        val characterMiscellaneousItemList: ArrayList<MiscellaneousItemData>,
        var characterPurseData: CharacterPurseData,
        val characterUUID: UUID
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
        var bronze: String, //TODO should this be a string or int?
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

object DataMaster: Hermez.HermezDataInterface {
    private val tag = "DataMaster"
    private val DATA_MASTER_KEY = "data_master_key"
    private val CHARACTER_HASHTABLE_KEY = "character_hashtable_key"
    private val CURRENT_CHARACTER_INFORMATION_KEY = "current_character_information_key"
    private var characterArray = ArrayList<CharacterInformation>() //TODO can this be moved down?
    private var characterHashtable = Hashtable<String, String>()
    private var otherPlayersArray = ArrayList<String>()
    private lateinit var hermez : Hermez //TODO how do i fisx
    private val phoneName = Build.MODEL
    var objectToNotify : DataMasterInterface? = null
    private lateinit var applicationDataMaster : Application

    //    lateinit var currentCharacter : CharacterInformation


    interface DataMasterInterface {
        //TODO do i want two functions to giveAllCharacters vs giveCurrentCharacter?
        fun giveCharacterInfo(characterInfo: CharacterInformation)
        fun giveAllCharactersInfo(characterInfoArray: ArrayList<CharacterInformation>)
        fun giveFriendsList(friendsList : ArrayList<String>)
    }

    fun initWith (application: Application){
        applicationDataMaster = application
        //TODO I would like to initilizae heremz here but memory leak
        hermez = Hermez(application.applicationContext, "_bag_of_holding._tcp")
        hermez.setDeviceName(phoneName)//TODO this needs to be a specific device id.
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
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Character to be saved is: $character")
        val characterInformationAsJSON = Gson().toJson(character)
        val characterName = character.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Is this always the last character created? How do I make this the last selected character???
        editor?.apply()
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterArray = ArrayList<CharacterInformation>()
        characterArray.add(character)
        objectToNotify?.giveAllCharactersInfo(characterArray)
    }

    fun changeCharacter(characterName: String){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Is this always the last character created? How do I make this the last selected character???
        editor?.apply()
        Log.d(tag, "Current character has been changed to $characterName")
    }

    fun retrieveAllCharactersInformation() : ArrayList<CharacterInformation>{
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
            if(sharedPrefs.contains(CHARACTER_HASHTABLE_KEY)){//if the file cabinent has a file called characters
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
                    return characterArray//TODO this isn't gonna work. is it? Can this array have characters not added that arent supposed to? If i call this 20 times does it add chars 20 times?
                }
            }else{//there is no file characters in the cabinet TODO that means never been saved??
                //what do I return
            }
        return characterArray
    }

    fun retrieveCharacterInformation() : CharacterInformation?{
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
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
                }else{//we don't have a current character
                    //what now?
                }
            }
        }else{//there is no file characters in the cabinet TODO that means never been saved??
            //what do I return
        }
        return null
    }


    //ITEMS
    //TODO Should I make this a inner class known as Saver?

    //SAVE
    fun saveItemArmor(characterOwner: CharacterInformation, armorItem: ArmorItemData){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be saved is: $armorItem character to save to is $characterOwner")
        characterOwner.characterArmorItemsList?.add(armorItem)
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Do we need todo this here. I need to understand how exactly this key is used better
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun saveItemWeapon(characterOwner: CharacterInformation, weaponItem: WeaponItemData){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be saved is: $weaponItem character to save to is $characterOwner")
        characterOwner.characterWeaponItemsList?.add(weaponItem)
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Do we need todo this here. I need to understand how exactly this key is used better
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun saveItemConsumable(characterOwner: CharacterInformation, consumableItem: ConsumablesItemData){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be saved is: $consumableItem character to save to is $characterOwner")
        characterOwner.characterConsumablesItemsList?.add(consumableItem)
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Do we need todo this here. I need to understand how exactly this key is used better
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun saveItemMiscellaneous(characterOwner: CharacterInformation, miscellaneousItem: MiscellaneousItemData){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be saved is: $miscellaneousItem character to save to is $characterOwner")
        characterOwner.characterMiscellaneousItemList?.add(miscellaneousItem)
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Do we need todo this here. I need to understand how exactly this key is used better
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    //TODO NEED TO DEAL WITH CURRENCY. How best to get type from the user.

    //DELETE TODO I need to deal with long touches and a contextual menu.
    fun deleteItemArmor(characterOwner: CharacterInformation, armorItem: ArmorItemData){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be removed is: $armorItem character to remove from is $characterOwner")
        characterOwner.characterArmorItemsList.remove(armorItem)//TODO I passed an object with a UUID here. Do i need to call the UUID specifically? will this work???????
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Do we need todo this here. I need to understand how exactly this key is used better
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun deleteItemWeapon(characterOwner: CharacterInformation, weaponItem: WeaponItemData){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be removed is: $weaponItem character to remove from is $characterOwner")
        characterOwner.characterWeaponItemsList.remove(weaponItem)//TODO I passed an object with a UUID here. Do i need to call the UUID specifically? will this work???????
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Do we need todo this here. I need to understand how exactly this key is used better
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun deleteItemConsumable(characterOwner: CharacterInformation, consumableItem: ConsumablesItemData){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be removed is: $consumableItem character to remove from is $characterOwner")
        characterOwner.characterConsumablesItemsList.remove(consumableItem)//TODO I passed an object with a UUID here. Do i need to call the UUID specifically? will this work???????
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Do we need todo this here. I need to understand how exactly this key is used better
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    fun deleteItemMiscellaneous(characterOwner: CharacterInformation, miscellaneousItem: MiscellaneousItemData){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Item to be removed is: $miscellaneousItem character to remove from is $characterOwner")
        characterOwner.characterMiscellaneousItemList.remove(miscellaneousItem)//TODO I passed an object with a UUID here. Do i need to call the UUID specifically? will this work???????
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Do we need todo this here. I need to understand how exactly this key is used better
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }

    //PURSE //TODO make more advanced at some point.
    fun saveMoney (characterOwner: CharacterInformation, newCharacterPurse: CharacterPurseData){
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "purse to be saved is: $newCharacterPurse character to save to is $characterOwner")
        characterOwner.characterPurseData = newCharacterPurse
        Log.d(tag, "Character information before GSON --> JSON = $characterOwner")
        val characterInformationAsJSON = Gson().toJson(characterOwner)
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        val characterName = characterOwner.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten? Do we need todo this here. I need to understand how exactly this key is used better
        editor?.apply()
        objectToNotify?.giveCharacterInfo(characterOwner)
    }


//    TRANSFER via Hermez
    fun transferItemArmor(characterOwner: CharacterInformation, newCharacterOwner: CharacterInformation, armorItem: ArmorItemData){

    }

    fun transferItemWeapon(characterOwner: CharacterInformation, newCharacterOwner: CharacterInformation, weaponItem: WeaponItemData){

    }

    fun transferItemConsumable(characterOwner: CharacterInformation, newCharacterOwner: CharacterInformation, consumableItem: ConsumablesItemData){

    }

    fun transferItemMiscellaneous(characterOwner: CharacterInformation, newCharacterOwner: CharacterInformation, miscellaneousItem: MiscellaneousItemData){

    }

    //HERMEZ SETUP
    fun findOtherPlayers() : ArrayList<String>{
        Log.d(tag, "findOtherPlayers array = $otherPlayersArray")
        return otherPlayersArray
    }

    fun sendWhisperToPlayer(){

    }

    fun resetFriendsDiscovery(){

    }

    override fun serviceStarted(serviceType: String, serviceName: String) {
        Log.d(tag, "serviceStarted called")
    }

    override fun serviceStopped(serviceType: String, serviceName: String) {
        Log.d(tag, "serviceStopped called")
    }

    override fun messageReceived(hermezMessage: Hermez.HermezMessage) {
        Log.d(tag, "messageReceived called")
        when (hermezMessage.message) {
            "Give me character details" -> {//todo get a better name for this string/key ENUM?? //also what do i do if I don't have a character? Do i need to reset? or only register my own service once I have a character?
                if (retrieveCharacterInformation() != null){//my own character exists and I can pass them back to whoever asked for it.
                    val characterAsJson = Gson().toJson(retrieveCharacterInformation())
                    val arrayList = ArrayList<Hermez.HermezDevice>()//todo my own phone is sending me a message. this is likely a hermez problem. we should fix it.
                    arrayList.add(hermezMessage.sendingDevice)
                    Log.d(tag, "characterAsJson = $characterAsJson")
                    Log.d(tag, "arrayList toSendTo = $arrayList")//todo i can give myself this array list here
                    hermez.sendMessageToDevices("Giving character details", characterAsJson,"002", arrayList)
                }
            }
            "Giving character details" ->{
                //todo do i need to give viewmodels data here for campfire???
            }
            "TRANSFER" ->{
                print("x == 2")
            }
            "COIN??" ->{
                print("x == 2")
            }
        }
    }

    override fun serviceFailed(serviceType: String, serviceName: String?, error: Hermez.HermezError) {
        Log.d(tag, "serviceFailed called")
    }

    override fun messageCannotBeSentToDevices(hermezMessage: Hermez.HermezMessage, error: Hermez.HermezError) {
        Log.d(tag, "messageCannotBeSentToDevices called")
    }

    override fun devicesFound(deviceList: ArrayList<Hermez.HermezDevice>) {
        Log.d(tag, "devicesFound called")
        for (item in deviceList){
            if (item.name == phoneName){
                //do not get character data
                //todo what if they have the same phoneName/type? UUID?
            }else{//not my device so get their character data.
                otherPlayersArray.add(item.name)
                hermez.sendMessageToDevices("Give me character details", "none", "001", deviceList) //our own device is listed here?
            }
        }

        //todo how do i go from a list of string names to a list of character objects?
        /*
        * steps
        * 1) get names of devices on networks
        * 2) ask all names of devices on network to send me a message with their character details as a json blob
        * 3) get their json blob and turn it into a character object here.
        * 4) return new characterArray to UI
        * */
    }

    override fun devicesFoundMutable(deviceList: MutableLiveData<Hermez.HermezDevice>) {
        TODO("Not yet implemented")
    }

    override fun resolveFailed(serviceType: String, serviceName: String, error: Hermez.HermezError) {
        Log.d(tag, "resolveFailed called")
    }
}