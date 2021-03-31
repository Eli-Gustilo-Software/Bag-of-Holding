package com.example.thebagofholding.ui.purse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.thebagofholding.R

class PurseFragment : Fragment() {

    private lateinit var purseViewModel: PurseViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        purseViewModel =
                ViewModelProvider(this).get(PurseViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_purse, container, false)
        purseViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }
}