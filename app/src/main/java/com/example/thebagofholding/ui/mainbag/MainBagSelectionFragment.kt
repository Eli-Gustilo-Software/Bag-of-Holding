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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.bag_holding_main_screen, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        weaponsListButton = view.findViewById(R.id.bag_weapons_button)
        super.onViewCreated(view, savedInstanceState)
        weaponsListButton.setOnClickListener(){
            findNavController().navigate(R.id.navigation_bag_holding_weapons_list)
        }
    }
}