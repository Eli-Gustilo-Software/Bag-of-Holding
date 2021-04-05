package com.example.thebagofholding.ui.character

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.DataMaster

class CharacterViewModel : ViewModel(), DataMaster.DataMasterInterface {
    private val TAG = "CharacterViewModel"
    val characterData = MutableLiveData<ArrayList<CharacterInformation>>()

    init {
        getCharacterInfo()
        DataMaster.objectToNotify = this
    }

    private fun getCharacterInfo(){
        //TODO this is supposed to get info from the dataMaster? but the override function is how the data master gives us data.....also there should always be a new character button regardless of zero size.
        val characterInfo = DataMaster.retrieveCharacterInformation()
        //TODO what is this grabbing? there is nothing to be grabbed???
        characterData.postValue(characterInfo)
    }

    override fun giveCharacterInfo(characterList: ArrayList<CharacterInformation>) {
        Log.d(TAG, "characters from giveCharacterInfo = $characterList")
        characterData.postValue(characterList)
    }
}