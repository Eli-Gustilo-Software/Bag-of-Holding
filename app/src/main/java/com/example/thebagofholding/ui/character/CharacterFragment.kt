package com.example.thebagofholding.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thebagofholding.R
import java.util.Observer

class CharacterFragment : Fragment() {
    private lateinit var mainBagButton: Button
    private lateinit var characterViewModel: CharacterViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_character, container, false)

//        characterViewModel =
//                ViewModelProvider(this).get(characterViewModel::class.java)
//        characterViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mainBagButton = view.findViewById(R.id.character_main_bag_button)
        super.onViewCreated(view, savedInstanceState)

        mainBagButton.setOnClickListener(){
            findNavController().navigate(R.id.navigation_bag_holding_main_screen)
        }
    }
}