package com.example.thebagofholding.ui.campfire

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.DataMaster

class CampfireViewModel : ViewModel(), DataMaster.DataMasterInterface {
    private val TAG = "CampfireViewModel"
    val characterData = MutableLiveData<ArrayList<String>>()

    init {
        getCharacterInfo()
        DataMaster.objectToNotify = this
    }

    private fun getCharacterInfo(){
        //TODO this is supposed to get info from the dataMaster? but the override function is how the data master gives us data.....also there should always be a new character button regardless of zero size.
        val otherPlayersList = DataMaster.findOtherPlayers()
        //TODO what is this grabbing? there is nothing to be grabbed??? It could be null? the character could not exsits
        characterData.postValue(otherPlayersList)
    }

    override fun giveCharacterInfo(characterInfo: CharacterInformation) {
        Log.d(TAG, "characters from giveCharacterInfo = $characterInfo")
    }

    override fun giveAllCharactersInfo(characterInfoArray: ArrayList<CharacterInformation>) {
        Log.d(TAG, "characters from giveAllCharacterInfo = $characterInfoArray")
    }

    override fun giveFriendsList(friendsList: ArrayList<String>) {
        Log.d(TAG, "friends from giveFriendsList = $friendsList")
        characterData.postValue(friendsList)
    }
}