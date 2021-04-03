package com.example.thebagofholding

import android.content.Context
import android.util.Log
import com.google.gson.Gson

data class ArmorItemData (
    val armorImage: Int,
    val armorName: String,
    val armorEffectsOne: String
)

data class CharacterInformation (
    val characterName : String,
    //TODO do i want these to be nullable? I could give the rando things that need it empty arrays? which is better?
    val characterArmorItemsList : ArrayList<ArmorItemData>?,
    val characterWeaponItemsList : ArrayList<WeaponItemData>?,
    val characterConsumablesItemsList : ArrayList<ConsumablesItemData>?,
    val characterMiscellaneousItemList : ArrayList<MiscellaneousItemData>?
        )

data class ConsumablesItemData (
    val consumablesImage: Int,
    val consumablesName: String,
    val consumablesEffectsOne: String
)

data class MiscellaneousItemData (
    val miscImage: Int,
    val miscName: String,
    val miscEffectsOne: String
)

data class WeaponItemData (
    val image: Int,
    val name: String,
    val weaponEffectOne: String
)

class DataMaster (val context: Context){
    private val tag = "DataMaster"
    //TODO figure out how to handle when it goes off line. currently we need the character name. but when the app is quit the character name will disappear and we will lose it???
    private val DATA_MASTER_KEY = "data_master_key"

    interface DataMasterInterface {
        //TODO what exactly do I want her
        /*
        * dataAvailable
        * weaponAvailable?? How precise?
        *
        * notify me array?
        *
        * characterFound? Solve the app closed problem???
        * */
    }

    fun saveCharacterInformation(character: CharacterInformation){
        val sharedPrefs = context.getSharedPreferences(DATA_MASTER_KEY, 0)
        val editor = sharedPrefs?.edit()
        Log.d(tag, "Character to be saved is: $character")
        val characterInformationAsJSON = Gson().toJson(character)
        editor?.putString(character.characterName, characterInformationAsJSON)
        editor?.apply()
        Log.d(tag, "Character information after GSON --> JSON = ${characterInformationAsJSON}")
    }

    fun retrieveCharacterInformation(characterName: String) : CharacterInformation{
        val sharedPrefs = context.getSharedPreferences(DATA_MASTER_KEY, 0)
        val gson = Gson()
        if(sharedPrefs.contains(characterName)){
            val characterInformationSaved = sharedPrefs.getString(characterName, null)
            val characterInformation = gson.fromJson(characterInformationSaved, CharacterInformation::class.java)
            Log.d(tag, "characterInformation found is $characterInformation")
            return characterInformation
        }else{
            //what do I return
            return CharacterInformation("Character not Found", null, null, null, null)
        }
    }


    //ITEMS
    //TODO Should I make this a inner class known as Saver?
    //TODO how do I keep track of these items? Duplicates? An id? use UUID object 

    //SAVE
    fun saveItemArmor(armorItem: ArmorItemData){

    }

    fun saveItemWeapon(weaponItem: WeaponItemData){

    }

    fun saveItemConsumable(consumableItem: ConsumablesItemData){

    }

    fun saveItemMiscellaneous(miscellaneousItem: MiscellaneousItemData){

    }

    //DELETE
    fun deleteItemArmor(armorItem: ArmorItemData){

    }

    fun deleteItemWeapon(weaponItem: WeaponItemData){

    }

    fun deleteItemConsumable(consumableItem: ConsumablesItemData){

    }

    fun deleteItemMiscellaneous(miscellaneousItem: MiscellaneousItemData){

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