package com.example.thebagofholding.ui.mainbag.weaponlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*
import com.example.thebagofholding.ui.items.NewMiscellaneousItemPopup
import com.example.thebagofholding.ui.items.NewWeaponItemPopup

class WeaponListFragment : Fragment(){
    private lateinit var weaponListRecyclerView: RecyclerView
    private lateinit var newItemButton : ImageView
    private lateinit var weaponListViewModel: WeaponListViewModel
    private var weaponListRecyclerAdapter: WeaponListRecyclerAdapter? = null
    private lateinit var currentCharacter : CharacterInformation

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_weapons_list, container, false)

        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = getString(
                    R.string.toolbar_title_weapons)

        //ViewModel and RecyclerViewAdapter
        weaponListViewModel = ViewModelProvider(this).get(WeaponListViewModel::class.java)
        weaponListViewModel.characterData.observe(viewLifecycleOwner, {
            currentCharacter = it
            weaponListRecyclerView = root.findViewById(R.id.weapons_list_recyclerView)
            if (weaponListRecyclerAdapter == null){ //create it
                weaponListRecyclerAdapter = WeaponListRecyclerAdapter(currentCharacter)
                weaponListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                weaponListRecyclerView.adapter = weaponListRecyclerAdapter
                weaponListRecyclerAdapter!!.notifyDataSetChanged()
            }else{//is created, so update.
                weaponListRecyclerAdapter!!.updateData(currentCharacter)
            }
        })

        //New Item Button
        newItemButton = root.findViewById(R.id.new_item_buttonw)
        newItemButton.setOnClickListener {
            val newItemPopup = NewWeaponItemPopup()
            newItemPopup.weaponitemDetailsDialogPopup(this.requireContext())
        }
        return root
    }
}