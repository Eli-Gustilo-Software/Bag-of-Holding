package com.eligustilo.thebagofholding.ui.campfire

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eligustilo.thebagofholding.DataMaster
import com.eligustilo.thebagofholding.R


class CampfireFragment : Fragment() {
    @Suppress("PrivatePropertyName")
    private val TAG = "NewItemFragment"
    private lateinit var campfireViewModel : CampfireViewModel
    private lateinit var campfireFindFriendsButton : Button
    private lateinit var campfireResetButton : Button
    private lateinit var campfireRecyclerView: RecyclerView
    private var campfireRecyclerAdapter: CampfireRecyclerAdapter? = null
    private var findFriendsClicked = false
    private var resetHermezClicked = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = getString(R.string.toolbar_yourcampfire_title)

        //inflate and handle view model setup
        val root = inflater.inflate(R.layout.fragment_campfire, container, false)
        campfireViewModel = ViewModelProvider(this).get(CampfireViewModel::class.java)
        campfireViewModel.characterData.observe(viewLifecycleOwner) {
            campfireRecyclerView = root.findViewById(R.id.fragment_campfire_recyclerview)
            if (campfireRecyclerAdapter == null){ //create it
                campfireRecyclerAdapter = CampfireRecyclerAdapter(DataMaster.findOtherPlayers())
                campfireRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                campfireRecyclerView.adapter = campfireRecyclerAdapter
                campfireRecyclerAdapter!!.notifyDataSetChanged()
            }else{//is created, so update.
                campfireRecyclerAdapter!!.updateData(DataMaster.findOtherPlayers())
                campfireRecyclerAdapter!!.notifyDataSetChanged()
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Initialize Buttons
        campfireFindFriendsButton = view.findViewById(R.id.campfire_find_friends_button)
        campfireResetButton = view.findViewById(R.id.campfire_reset_button)

        super.onViewCreated(view, savedInstanceState)

        //Set button listeners
        campfireFindFriendsButton.setOnClickListener {
            if (!findFriendsClicked){//we haven't clicked it already recently so go ahead and reset
                Log.d(TAG, "button clicked to get all friends")
                DataMaster.resetDiscovery()
                findFriendsClicked = true
                Toast.makeText(context, "Searching for nearby devices on your Network.", Toast.LENGTH_LONG).show()
                Handler().postDelayed({ // delay to ensure pairing is done
                    findFriendsClicked = false
                }, 10000)
            }else{//we clicked it in the last 10 seconds so wait.
                Toast.makeText(context, "We are already searching. One second...", Toast.LENGTH_LONG).show()
            }
        }

        campfireResetButton.setOnClickListener {
            if (!resetHermezClicked && !findFriendsClicked){//we haven't clicked it already recently so go ahead and reset
                Log.d(TAG, "button clicked to get all friends")
                DataMaster.resetDiscovery()
                resetHermezClicked = true
                Toast.makeText(context, "Resetting your device's service.", Toast.LENGTH_LONG).show()
                Handler().postDelayed({ // delay to ensure pairing is done
                    resetHermezClicked = false
                }, 15000)
            }else{//we clicked it in the last 10 seconds so wait.
                Toast.makeText(context, "We are resetting this could take a moment...", Toast.LENGTH_LONG).show()
            }
        }
    }
}