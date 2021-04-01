package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.thebagofholding.R

class MainBagSelectionFragment : Fragment() {
    private lateinit var weaponsListButton : Button
    private lateinit var armorListButton : Button
    private lateinit var consumablesListButton : Button
    private lateinit var miscListButton : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.bag_holding_main_screen, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Setup Buttons
        weaponsListButton = view.findViewById(R.id.bag_weapons_button)
        armorListButton = view.findViewById(R.id.bag_armor_button)
        consumablesListButton = view.findViewById(R.id.bag_consumables_button)
        miscListButton = view.findViewById(R.id.bag_misc_button)

        super.onViewCreated(view, savedInstanceState)

        //On Click Listeners for Buttons
        weaponsListButton.setOnClickListener(){
            findNavController().navigate(R.id.navigation_bag_holding_weapons_list)
        }

        armorListButton.setOnClickListener(){
            findNavController().navigate(R.id.navigation_bag_holding_armor_list)
        }

        consumablesListButton.setOnClickListener(){
            findNavController().navigate(R.id.navigation_bag_holding_consumables_list)
        }

        miscListButton.setOnClickListener(){
            findNavController().navigate(R.id. navigation_bag_holding_misc_list)
        }
    }
}