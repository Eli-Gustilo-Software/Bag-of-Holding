package com.eligustilo.thebagofholding

import android.content.Context
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataMasterTest : DataMaster.DataMasterInterface {
    private var otherPlayerArray : ArrayList<OtherPlayerCharacterInformation> = ArrayList()

    @Test
    fun testFindDevice() {
        val tag = "testFindDevice"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        val mainActivity = launch(MainActivity::class.java)
        DataMaster.objectToNotify = this
        DataMaster.findOtherPlayers()
        Thread.sleep(10000)
        Assert.assertTrue("players = ${otherPlayerArray}", otherPlayerArray.size > 0)
    }

    override fun giveCharacterInfo(characterInfo: CharacterInformation) {
    }

    override fun giveAllCharactersInfo(characterInfoArray: ArrayList<CharacterInformation>) {
    }

    override fun giveFriendsList(friendsList: ArrayList<OtherPlayerCharacterInformation>) {
        otherPlayerArray = friendsList
    }
}