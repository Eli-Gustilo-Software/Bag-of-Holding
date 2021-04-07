package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*

class WeaponListFragment : Fragment(){
    private lateinit var weaponListRecyclerView: RecyclerView
    private var weaponListRecyclerAdapter: WeaponListRecyclerAdapter? = null
    private var weaponsListArray = ArrayList<WeaponItemData>()
    private lateinit var currentCharacter : CharacterInformation

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!//TODO is this current character infromation?? Rename?
            for (item in currentCharacter.characterWeaponItemsList!!){
                weaponsListArray.add(item)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_weapons_list, container, false)
        weaponListRecyclerView = root.findViewById(R.id.weapons_list_recyclerView)
        if (weaponListRecyclerAdapter == null){ //create it
            weaponListRecyclerAdapter = WeaponListRecyclerAdapter(weaponsListArray)
            weaponListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
            weaponListRecyclerView.adapter = weaponListRecyclerAdapter
            weaponListRecyclerAdapter!!.notifyDataSetChanged()
        }else{//is created, so update.

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}