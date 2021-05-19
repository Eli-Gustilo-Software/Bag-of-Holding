package com.example.thebagofholding.ui.campfire

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.OtherPlayerCharacterInformation

class CampfireViewModel : ViewModel(), DataMaster.DataMasterInterface {
    private val tag = "CampfireViewModel"
    val characterData = MutableLiveData<ArrayList<OtherPlayerCharacterInformation>>()

    init {
        getCharacterInfo()
        DataMaster.objectToNotify = this
    }

    private fun getCharacterInfo(){
        val otherPlayersList = DataMaster.findOtherPlayers()
        characterData.postValue(otherPlayersList)
    }

    override fun giveCharacterInfo(characterInfo: CharacterInformation) {
        Log.d(tag, "characters from giveCharacterInfo = $characterInfo")
    }

    override fun giveAllCharactersInfo(characterInfoArray: ArrayList<CharacterInformation>) {
        Log.d(tag, "characters from giveAllCharacterInfo = $characterInfoArray")
    }

    override fun giveFriendsList(friendsList: ArrayList<OtherPlayerCharacterInformation>) {
        Log.d(tag, "friends from giveFriendsList = $friendsList")
        characterData.postValue(friendsList)
    }
}
