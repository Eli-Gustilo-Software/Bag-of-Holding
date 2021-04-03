package com.example.thebagofholding.ui.character

import android.os.Bundle
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
import com.example.thebagofholding.R
import com.example.thebagofholding.ui.items.NewItemViewModel

class CharacterCreationFragment : Fragment() {
    private lateinit var newItemViewModel: NewItemViewModel
    private lateinit var characterCreationRecyclerView: RecyclerView
    private var characterCreationRecyclerAdapter: CharacterCreationRecyclerAdapter? = null
    private var characterCreationRecyclerCharacterArray = ArrayList<CharacterInformation>()


    private var recyclerViewFirstCell = CharacterInformation("Create a New Character",null,null,null,null)

    init {
        //TODO make this better. Look more pretty and work smoother.
        characterCreationRecyclerCharacterArray.add(recyclerViewFirstCell)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.character_creation_screen, container, false)
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
}