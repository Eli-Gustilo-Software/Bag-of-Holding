package com.example.thebagofholding.ui.character

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.OtherPlayerCharacterInformation

class CharacterCreationViewModel : ViewModel(), DataMaster.DataMasterInterface {
    private val tag = "CharacterViewModel"
    val characterData = MutableLiveData<ArrayList<CharacterInformation>>()

    init {
        getCharacterInfo()
        DataMaster.objectToNotify = this
    }

    private fun getCharacterInfo(){
        val characterInfo = DataMaster.retrieveAllCharactersInformation()
        characterData.postValue(characterInfo)
    }

    override fun giveCharacterInfo(characterInfo: CharacterInformation) {
        Log.d(tag, "characters from giveCharacterInfo = $characterInfo")
//        characterData.postValue(characterList)
    }

    override fun giveAllCharactersInfo(characterInfoArray: ArrayList<CharacterInformation>) {
        Log.d(tag, "characters from giveAllCharacterInfo = $characterInfoArray")
        characterData.postValue(characterInfoArray)
    }

    override fun giveFriendsList(friendsList: ArrayList<OtherPlayerCharacterInformation>) {
        Log.d(tag, "friends from giveFriendsList = $friendsList")
    }
}