package com.eligustilosoftware.thebagofholding.ui.mainbag.weaponlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eligustilosoftware.thebagofholding.CharacterInformation
import com.eligustilosoftware.thebagofholding.DataMaster
import com.eligustilosoftware.thebagofholding.OtherPlayerCharacterInformation

class WeaponListViewModel : ViewModel(), DataMaster.DataMasterInterface {
    private val tag = "WeaponListViewModel"
    val characterData = MutableLiveData<CharacterInformation>()

    init {
        getCharacterInfo()
        DataMaster.objectToNotify = this
    }

    private fun getCharacterInfo(){
        val characterInfo = DataMaster.retrieveCharacterInformation()
        characterData.postValue(characterInfo!!)
    }

    override fun giveCharacterInfo(characterInfo: CharacterInformation) {
        Log.d(tag, "characters from giveCharacterInfo = $characterInfo")
        characterData.postValue(characterInfo)
    }

    override fun giveAllCharactersInfo(characterInfoArray: ArrayList<CharacterInformation>) {
        Log.d(tag, "characters from giveAllCharacterInfo = $characterInfoArray")
    }

    override fun giveFriendsList(friendsList: ArrayList<OtherPlayerCharacterInformation>) {
        Log.d(tag, "friends from giveFriendsList = $friendsList")
    }
}