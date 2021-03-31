package com.example.thebagofholding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thebagofholding.CharacterCreationRecycler
import com.example.thebagofholding.R
import com.example.thebagofholding.ui.notifications.NotificationsViewModel

class CharacterCreationFragment : Fragment() {
    private lateinit var notificationsViewModel: NotificationsViewModel


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