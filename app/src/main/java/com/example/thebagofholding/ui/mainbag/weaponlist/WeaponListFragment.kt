package com.example.thebagofholding.ui.mainbag.weaponlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*

class WeaponListFragment : Fragment(){
    private lateinit var weaponListRecyclerView: RecyclerView
    private lateinit var weaponListViewModel: WeaponListViewModel
    private lateinit var weaponListOwnerTextView : TextView
    private var weaponListRecyclerAdapter: WeaponListRecyclerAdapter? = null
    private var weaponsListArray = ArrayList<WeaponItemData>()
    private lateinit var currentCharacter : CharacterInformation

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!//TODO is this current character information?? Rename?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_weapons_list, container, false)

        //ViewModel and RecyclerViewAdapter
        weaponListViewModel = ViewModelProvider(this).get(WeaponListViewModel::class.java)
        weaponListViewModel.characterData.observe(viewLifecycleOwner, Observer {
            weaponListRecyclerView = root.findViewById(R.id.weapons_list_recyclerView)
            if (weaponListRecyclerAdapter == null){ //create it
                weaponListRecyclerAdapter = WeaponListRecyclerAdapter(currentCharacter)
                weaponListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                weaponListRecyclerView.adapter = weaponListRecyclerAdapter
                weaponListRecyclerAdapter!!.notifyDataSetChanged()
            }else{//is created, so update.
                weaponListRecyclerAdapter!!.updateData(currentCharacter)
                weaponListRecyclerAdapter!!.notifyDataSetChanged()
            }
        })

        //Character Name TextView
        weaponListOwnerTextView = root.findViewById(R.id.weapons_list_title)
        weaponListOwnerTextView.text = "${currentCharacter.characterName}'s Weapons"

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}