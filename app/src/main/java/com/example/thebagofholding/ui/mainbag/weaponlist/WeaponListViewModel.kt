package com.example.thebagofholding.ui.mainbag.weaponlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.OtherPlayerCharacterInformation

class WeaponListViewModel : ViewModel(), DataMaster.DataMasterInterface {
    private val TAG = "WeaponListViewModel"
    val characterData = MutableLiveData<CharacterInformation>()

    init {
        getCharacterInfo()
        DataMaster.objectToNotify = this
    }

    private fun getCharacterInfo(){
        //TODO this is supposed to get info from the dataMaster? but the override function is how the data master gives us data.....also there should always be a new character button regardless of zero size.
        val characterInfo = DataMaster.retrieveCharacterInformation()
        //TODO what is this grabbing? there is nothing to be grabbed??? It could be null? the character could not exsits
        characterData.postValue(characterInfo!!)
    }

    override fun giveCharacterInfo(characterInfo: CharacterInformation) {
        Log.d(TAG, "characters from giveCharacterInfo = $characterInfo")
        characterData.postValue(characterInfo)
    }

    override fun giveAllCharactersInfo(characterInfoArray: ArrayList<CharacterInformation>) {
        Log.d(TAG, "characters from giveAllCharacterInfo = $characterInfoArray")
    }

    override fun giveFriendsList(friendsList: ArrayList<OtherPlayerCharacterInformation>) {
        Log.d(TAG, "friends from giveFriendsList = $friendsList")
    }
}