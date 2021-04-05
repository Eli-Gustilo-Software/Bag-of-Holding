package com.example.thebagofholding

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class ArmorItemData(
        val armorImage: Int,
        val armorName: String,
        val armorEffectsOne: String
)

data class CharacterInformation(
        val characterName: String,
        //TODO do i want these to be nullable? I could give the rando things that need it empty arrays? which is better?
        val characterArmorItemsList: ArrayList<ArmorItemData>?,
        val characterWeaponItemsList: ArrayList<WeaponItemData>?,
        val characterConsumablesItemsList: ArrayList<ConsumablesItemData>?,
        val characterMiscellaneousItemList: ArrayList<MiscellaneousItemData>?
)

data class ConsumablesItemData(
        val consumablesImage: Int,
        val consumablesName: String,
        val consumablesEffectsOne: String
)

data class MiscellaneousItemData(
        val miscImage: Int,
        val miscName: String,
        val miscEffectsOne: String
)

data class WeaponItemData(
        val image: Int,
        val name: String,
        val weaponEffectOne: String
)

object DataMaster {
    private val tag = "DataMaster"
    //TODO figure out how to handle when it goes off line. currently we need the character name. but when the app is quit the character name will disappear and we will lose it???
    private val DATA_MASTER_KEY = "data_master_key"
    private val CHARACTER_HASHTABLE_KEY = "character_hashtable_key"
    private val CURRENT_CHARACTER_INFORMATION_KEY = "current_character_information_key"
    private var characterArray = ArrayList<CharacterInformation>()
    private var characterHashtable = Hashtable<String, String>()
    var objectToNotify : DataMasterInterface? = null
    lateinit var applicationDataMaster : Application


    interface DataMasterInterface {
        fun giveCharacterInfo(characterList: ArrayList<CharacterInformation>)
    }

    fun initWith (application: Application){
        applicationDataMaster = application
    }

    fun saveCharacterInformation(character: CharacterInformation){
        //make a file cabinet with DATA_MASTER_KEY
        //take characterInformation Data Class and turn it into JSON
        //put that JSON into the characterHashtable with the character name as key
        //take that hashtable turn it into JSON
        //store that JSON in a file in the DATA_MASTER_KEY cabinet with key CHARACTER_HASHTABLE_KEY
        //store characterName with CURRENT_CHARACTER_INFORMATION_KEY so we can use that name to run through the entire hashmap to grab the current character.
        //last step may not be necessary.


        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Character to be saved is: $character")
        val characterInformationAsJSON = Gson().toJson(character)
        val characterName = character.characterName
        characterHashtable.put(characterName, characterInformationAsJSON)
        val characterHashtableJSON = Gson().toJson(characterHashtable)
        editor?.putString(CHARACTER_HASHTABLE_KEY, characterHashtableJSON)
        editor?.putString(CURRENT_CHARACTER_INFORMATION_KEY, characterName)//TODO this is always one? Can be overwritten?

        editor?.apply()
        Log.d(tag, "Character information after GSON --> JSON = $characterInformationAsJSON")
        characterArray.add(character)
        objectToNotify?.giveCharacterInfo(characterArray)
    }

    fun retrieveCharacterInformation() : ArrayList<CharacterInformation>{
        val sharedPrefs = applicationDataMaster.applicationContext.getSharedPreferences(DATA_MASTER_KEY, 0)
        //TODO does this characterKEY thing allow for multiple characters?
            if(sharedPrefs.contains(CHARACTER_HASHTABLE_KEY)){//if the file cabinent has a file called characters
                val savedCharacterInformationHashtableJSON = sharedPrefs.getString(CHARACTER_HASHTABLE_KEY, null) //get the characters file
                if (savedCharacterInformationHashtableJSON != null){ //if that file isn't blank
                    characterHashtable = Gson().fromJson(savedCharacterInformationHashtableJSON, Hashtable<String, String>()::class.java) //turn the file from JSON into a hashtable.
                    if (sharedPrefs.contains(CURRENT_CHARACTER_INFORMATION_KEY)){ //if we have a current character//TODO is this right? this calls a specific character. we want to call many.
                        val currentCharacterName = sharedPrefs.getString(CURRENT_CHARACTER_INFORMATION_KEY, null) //TODO how will this work with multiple characters?
                        val currentCharacterJSON = characterHashtable[currentCharacterName] //give the character name to the hashtable to get back the characterInformation object as a JSON blob.
                        if (currentCharacterJSON != null){ //means that the character Data Object exists as JSON
                            val characterObject = Gson().fromJson(currentCharacterJSON, CharacterInformation::class.java) //turn into character object
                            Log.d(tag, "Character to be returned is $characterObject")
                            return characterArray//TODO this isn't gonna work.
                        }
                    }else{//we don't have a current character
                        //what now?
                    }
                }
            }else{//there is no file characters in the cabinet TODO that means never been saved??
                //what do I return
            }
        return characterArray
    }


    //ITEMS
    //TODO Should I make this a inner class known as Saver?
    //TODO how do I keep track of these items? Duplicates? An id? use UUID object 

    //SAVE
    fun saveItemArmor(characterOwner: CharacterInformation, armorItem: ArmorItemData){

    }

    fun saveItemWeapon(characterOwner: CharacterInformation, weaponItem: WeaponItemData){

    }

    fun saveItemConsumable(characterOwner: CharacterInformation, consumableItem: ConsumablesItemData){

    }

    fun saveItemMiscellaneous(characterOwner: CharacterInformation, miscellaneousItem: MiscellaneousItemData){

    }

    //DELETE
    fun deleteItemArmor(characterOwner: CharacterInformation, armorItem: ArmorItemData){

    }

    fun deleteItemWeapon(characterOwner: CharacterInformation, weaponItem: WeaponItemData){

    }

    fun deleteItemConsumable(characterOwner: CharacterInformation, consumableItem: ConsumablesItemData){

    }

    fun deleteItemMiscellaneous(characterOwner: CharacterInformation, miscellaneousItem: MiscellaneousItemData){

    }

    //TRANSFER via Hermez
//    fun transferItemArmor(armorItem: ArmorItemData){
//
//    }
//
//    fun transferItemWeapon(weaponItem: WeaponItemData){
//
//    }
//
//    fun transferItemConsumable(consumableItem: ConsumablesItemData){
//
//    }
//
//    fun transferItemMiscellaneous(miscellaneousItem: MiscellaneousItemData){
//
//    }
}