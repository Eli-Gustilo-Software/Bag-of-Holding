package com.example.thebagofholding.ui.campfire

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.R
import com.example.thebagofholding.ui.items.NewItemViewModel
import com.example.thebagofholding.ui.mainbag.armorlist.ArmorListRecyclerAdapter

class CampfireFragment : Fragment() {
    private val TAG = "NewItemFragment"
    private lateinit var campfireViewModel : CampfireViewModel
    private lateinit var campfireFindFriendsButton : Button
    private lateinit var campfireRecyclerView: RecyclerView
    private var campfireRecyclerAdapter: CampfireRecyclerAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_campfire, container, false)
        campfireViewModel = ViewModelProvider(this).get(CampfireViewModel::class.java)
        campfireViewModel.characterData.observe(viewLifecycleOwner, Observer {
            campfireRecyclerView = root.findViewById(R.id.fragment_campfire_recyclerview)
            if (campfireRecyclerAdapter == null){ //create it
                campfireRecyclerAdapter = CampfireRecyclerAdapter(DataMaster.findOtherPlayers())//todo get this from dataMaster!!
                campfireRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                campfireRecyclerView.adapter = campfireRecyclerAdapter
                campfireRecyclerAdapter!!.notifyDataSetChanged()
            }else{//is created, so update.
                campfireRecyclerAdapter!!.updateData(DataMaster.findOtherPlayers())//todo get this from dataMaster!!
                campfireRecyclerAdapter!!.notifyDataSetChanged()
            }
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Initialize Buttons
        campfireFindFriendsButton = view.findViewById(R.id.campfire_find_friends_button)

        super.onViewCreated(view, savedInstanceState)

        //Set button listeners
        campfireFindFriendsButton.setOnClickListener(){
            Log.d(TAG, "button clicked to get all friends")
            DataMaster.findOtherPlayers()
        }
    }
}