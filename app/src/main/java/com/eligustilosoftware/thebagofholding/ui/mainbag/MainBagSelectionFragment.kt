package com.eligustilosoftware.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eligustilosoftware.thebagofholding.R


class MainBagSelectionFragment : Fragment() {
    private lateinit var weaponsListButton : ImageButton
    private lateinit var armorListButton : ImageButton
    private lateinit var consumablesListButton : ImageButton
    private lateinit var miscListButton : ImageButton

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = "Bag of Holding"

        val root = inflater.inflate(R.layout.bag_holding_main_screen, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        (activity as AppCompatActivity?)!!.supportActionBar!!.title = "${DataMaster.retrieveCharacterInformation()?.characterName}'s Bag of Holding"
//        (requireActivity() as MainActivity).title = "${DataMaster.retrieveCharacterInformation()?.characterName}'s Bag of Holding"


        //Setup Buttons
        weaponsListButton = view.findViewById(R.id.bag_weapons_button)
        armorListButton = view.findViewById(R.id.bag_armor_button)
        consumablesListButton = view.findViewById(R.id.bag_consumables_button)
        miscListButton = view.findViewById(R.id.bag_misc_button)

        super.onViewCreated(view, savedInstanceState)

        //On Click Listeners for Buttons
        //TODO Make sure items can have multiple stacks of them. Aka 10 health potions.

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
            findNavController().navigate(R.id.navigation_bag_holding_misc_list)
        }
    }
}