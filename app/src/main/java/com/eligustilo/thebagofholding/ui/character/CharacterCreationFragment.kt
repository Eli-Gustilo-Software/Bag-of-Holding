package com.eligustilo.thebagofholding.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eligustilo.thebagofholding.CharacterInformation
import com.eligustilo.thebagofholding.R


class CharacterCreationFragment : Fragment() {
    private lateinit var characterCreationViewModel : CharacterCreationViewModel
    private lateinit var characterCreationRecyclerView: RecyclerView
    private val fragment = this
    private var characterCreationRecyclerAdapter: CharacterCreationRecyclerAdapter? = null
    private var characterCreationRecyclerCharacterArray = ArrayList<CharacterInformation>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_character_creation, container, false)
        characterCreationRecyclerView = root.findViewById(R.id.character_creation_recyclerview)

        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = getString(
                    R.string.toolbar_title_character_selection)


        //ViewModel and RecyclerViewAdapter
        characterCreationViewModel = ViewModelProvider(this).get(CharacterCreationViewModel::class.java)
        characterCreationViewModel.characterData.observe(viewLifecycleOwner) {
            characterCreationRecyclerCharacterArray = it
            if (characterCreationRecyclerAdapter == null) {//create it
                characterCreationRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                characterCreationRecyclerAdapter = CharacterCreationRecyclerAdapter(characterCreationRecyclerCharacterArray, fragment)
                characterCreationRecyclerView.adapter = characterCreationRecyclerAdapter
                characterCreationRecyclerAdapter!!.notifyDataSetChanged()
            } else {//it is created and we need to sync data.
                characterCreationRecyclerAdapter!!.updateData(characterCreationRecyclerCharacterArray)
            }
        }
        return root
    }
}
