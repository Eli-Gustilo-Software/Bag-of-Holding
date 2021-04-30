package com.example.thebagofholding.ui.character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.com.kotlinapp.OnSwipeTouchListener
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.R

class CharacterCreationFragment : Fragment() {
    private lateinit var characterCreationViewModel : CharacterCreationViewModel
    private lateinit var characterCreationRecyclerView: RecyclerView
    private var characterCreationRecyclerAdapter: CharacterCreationRecyclerAdapter? = null
    private var characterCreationRecyclerCharacterArray = ArrayList<CharacterInformation>()
    private var TAG = "CharacterCreationFragment"

    init {
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_character_creation, container, false)
        characterCreationRecyclerView = root.findViewById(R.id.character_creation_recyclerview)

        //ViewModel and RecyclerViewAdapter
        characterCreationViewModel = ViewModelProvider(this).get(CharacterCreationViewModel::class.java)
        characterCreationViewModel.characterData.observe(viewLifecycleOwner, Observer {
            characterCreationRecyclerCharacterArray = it
            if (characterCreationRecyclerAdapter == null){//create it
                characterCreationRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                characterCreationRecyclerAdapter = CharacterCreationRecyclerAdapter(characterCreationRecyclerCharacterArray)
                characterCreationRecyclerView.adapter = characterCreationRecyclerAdapter
                characterCreationRecyclerAdapter!!.notifyDataSetChanged()
            }else{//it is created and we need to sync data.
                characterCreationRecyclerAdapter!!.updateData(characterCreationRecyclerCharacterArray)
            }
        })

        //handle swipe listeners
        root.setOnTouchListener(object : OnSwipeTouchListener(this.context) {
            override fun onSwipeLeft() {
                Log.d(tag, "Swipe left.")
                findNavController().navigate(R.id.navigation_character)
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
            }
            override fun onSwipeUp() {
                super.onSwipeUp()
            }
            override fun onSwipeDown() {
                super.onSwipeDown()
            }

        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {




        super.onViewCreated(view, savedInstanceState)
    }
}