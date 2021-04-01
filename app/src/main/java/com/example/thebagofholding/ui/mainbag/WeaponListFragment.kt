package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.R
import com.example.thebagofholding.WeaponListRecyclerAdapter

class WeaponListFragment : Fragment(){
    private lateinit var weaponListRecyclerView: RecyclerView
    private var weaponListRecyclerAdapter: WeaponListRecyclerAdapter? = null
    private var weaponsListTestArray = ArrayList<String>()

    init {
        weaponsListTestArray.add("Sword of Doom")
        weaponsListTestArray.add("Sword of t")
        weaponsListTestArray.add("Sword of f")
        weaponsListTestArray.add("Sword of g")
        weaponsListTestArray.add("Sword of b")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_weapons_list, container, false)
        weaponListRecyclerView = root.findViewById(R.id.weapons_list_recyclerView)
        if (weaponListRecyclerAdapter == null){ //create it
            weaponListRecyclerAdapter = WeaponListRecyclerAdapter(weaponsListTestArray)
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