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

class CharacterCreationFragment : Fragment() {
    private lateinit var characterViewModel : CharacterViewModel
    private lateinit var characterCreationRecyclerView: RecyclerView
    private var characterCreationRecyclerAdapter: CharacterCreationRecyclerAdapter? = null
    private var characterCreationRecyclerCharacterArray = ArrayList<CharacterInformation>()
    private var TAG = "CharacterCreationFragment"


    private var recyclerViewFirstCell = CharacterInformation("Create a New Character",null,null,null,null)

    init {
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_character_creation, container, false)
        characterCreationRecyclerView = root.findViewById(R.id.character_creation_recyclerview)




        characterViewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        characterViewModel.characterData.observe(viewLifecycleOwner, Observer {
            characterCreationRecyclerCharacterArray = it
            if (characterCreationRecyclerAdapter == null){//create it
                characterCreationRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                characterCreationRecyclerAdapter = CharacterCreationRecyclerAdapter(characterCreationRecyclerCharacterArray)
                characterCreationRecyclerView.adapter = characterCreationRecyclerAdapter
                characterCreationRecyclerAdapter!!.notifyDataSetChanged()
            }else{//it is created and we need to sync data.
                characterCreationRecyclerAdapter!!.updateData(characterCreationRecyclerCharacterArray)
                characterCreationRecyclerAdapter!!.notifyDataSetChanged()
            }
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}