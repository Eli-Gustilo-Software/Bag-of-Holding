package com.example.thebagofholding.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thebagofholding.R
import com.example.thebagofholding.ui.items.NewItemViewModel

class CharacterCreationFragment : Fragment() {
    private lateinit var newItemViewModel: NewItemViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.character_creation_screen, container, false)

//        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
//        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}