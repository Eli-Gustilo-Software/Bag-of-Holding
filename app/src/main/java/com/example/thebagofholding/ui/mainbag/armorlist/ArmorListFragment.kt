package com.example.thebagofholding.ui.mainbag.armorlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.ArmorItemData
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.R

class ArmorListFragment : Fragment(){
    private lateinit var armorListRecyclerView: RecyclerView
    private lateinit var armorListViewModel: ArmorListViewModel
    private var armorListRecyclerAdapter: ArmorListRecyclerAdapter? = null
    private var armorListArray = ArrayList<ArmorItemData>()
    private lateinit var currentCharacter : CharacterInformation

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!
            for (item in currentCharacter.characterArmorItemsList){
                armorListArray.add(item)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_armor_apparal_list, container, false)

        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = getString(
                    R.string.toolbar_title_armor)

        //ViewModel and RecyclerViewAdapter
        armorListViewModel = ViewModelProvider(this).get(ArmorListViewModel::class.java)
        armorListViewModel.characterData.observe(viewLifecycleOwner, {
            currentCharacter = it
            armorListRecyclerView = root.findViewById(R.id.armor_list_recyclerView)
            if (armorListRecyclerAdapter == null){ //create it
                armorListRecyclerAdapter = ArmorListRecyclerAdapter(currentCharacter)
                armorListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                armorListRecyclerView.adapter = armorListRecyclerAdapter
                armorListRecyclerAdapter!!.notifyDataSetChanged()
            }else{//is created, so update.
                armorListRecyclerAdapter!!.updateData(currentCharacter)
                armorListRecyclerAdapter!!.notifyDataSetChanged()
            }
        })

        return root
    }
}