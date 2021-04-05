package com.example.thebagofholding.ui.character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.CharacterCreationRecyclerAdapter
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.R
import com.example.thebagofholding.ui.items.NewItemViewModel
import kotlin.math.log

class CharacterCreationFragment : Fragment(), DataMaster.DataMasterInterface {
    private lateinit var newItemViewModel: NewItemViewModel
    private lateinit var characterCreationRecyclerView: RecyclerView
    private var characterCreationRecyclerAdapter: CharacterCreationRecyclerAdapter? = null
    private var characterCreationRecyclerCharacterArray = ArrayList<CharacterInformation>()
    private var TAG = "CharacterCreationFragment"


    private var recyclerViewFirstCell = CharacterInformation("Create a New Character",null,null,null,null)

    init {
        DataMaster.objectToNotify = this
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_character_creation, container, false)
        characterCreationRecyclerView = root.findViewById(R.id.character_creation_recyclerview)

        if (characterCreationRecyclerAdapter == null){//create it
            characterCreationRecyclerAdapter = CharacterCreationRecyclerAdapter(characterCreationRecyclerCharacterArray)
            characterCreationRecyclerView.layoutManager = LinearLayoutManager(this.activity)
            characterCreationRecyclerView.adapter = characterCreationRecyclerAdapter
            characterCreationRecyclerAdapter!!.notifyDataSetChanged()
        }else{//it is created and we need to sync data.

        }

        newItemViewModel = ViewModelProvider(this).get(NewItemViewModel::class.java)
        newItemViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun giveCharacterInfo(characterList: ArrayList<CharacterInformation>) {
        recyclerViewFirstCell = characterList.first()
        Log.d(TAG, "character from giveCharacterInfo = $recyclerViewFirstCell")
        characterCreationRecyclerAdapter?.updateData(characterList)
    }
}