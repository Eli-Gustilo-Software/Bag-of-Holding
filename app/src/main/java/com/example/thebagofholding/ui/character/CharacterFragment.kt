package com.example.thebagofholding.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.R

class CharacterFragment : Fragment() {
    private lateinit var mainBagButton: ImageButton
    private lateinit var coinPurseButton : ImageButton
    private lateinit var newItemButton: ImageButton
    private lateinit var currentCharacter : CharacterInformation

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_character, container, false)


        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = "${DataMaster.retrieveCharacterInformation()?.characterName}"


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //CharacterName TextView
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!
        }else{
            findNavController().navigate(R.id.navigation_character_creation_screen)
            Toast.makeText(this.context, "Please create or select a character first.", Toast.LENGTH_LONG).show()
        }

        //Buttons
        mainBagButton = view.findViewById(R.id.character_main_bag_button)
        super.onViewCreated(view, savedInstanceState)

        mainBagButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_bag_holding_main_screen)
        }

        coinPurseButton = view.findViewById(R.id.character_coin_purse_button)
        super.onViewCreated(view, savedInstanceState)

        coinPurseButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_purse)
        }

        newItemButton = view.findViewById(R.id.charcater_new_item_button)
        super.onViewCreated(view, savedInstanceState)

        newItemButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_new_item)
        }
    }
}