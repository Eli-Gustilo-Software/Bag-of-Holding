package com.example.thebagofholding.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thebagofholding.R

class CharacterFragment : Fragment() {
    private lateinit var mainBagButton: Button
    private lateinit var coinPurseButton : Button
    private lateinit var newItemButton: Button


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_character, container, false)


        //TODO figure out how to use the view model


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        //Buttons
        mainBagButton = view.findViewById(R.id.character_main_bag_button)
        super.onViewCreated(view, savedInstanceState)

        mainBagButton.setOnClickListener(){
            findNavController().navigate(R.id.navigation_bag_holding_main_screen)
        }

        coinPurseButton = view.findViewById(R.id.character_coin_purse_button)
        super.onViewCreated(view, savedInstanceState)

        coinPurseButton.setOnClickListener(){
            findNavController().navigate(R.id.navigation_purse)
        }

        newItemButton = view.findViewById(R.id.charcater_new_item_button)
        super.onViewCreated(view, savedInstanceState)

        newItemButton.setOnClickListener(){
            findNavController().navigate(R.id.navigation_new_item)
        }
    }
}