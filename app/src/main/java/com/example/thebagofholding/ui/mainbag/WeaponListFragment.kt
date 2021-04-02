package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.R
import com.example.thebagofholding.WeaponItemData
import com.example.thebagofholding.WeaponListRecyclerAdapter

class WeaponListFragment : Fragment(){
    private lateinit var weaponListRecyclerView: RecyclerView
    private var weaponListRecyclerAdapter: WeaponListRecyclerAdapter? = null
    private var weaponsListTestArray = ArrayList<WeaponItemData>()

    init {
        val testWeapon2 = WeaponItemData((R.drawable.ic_armor), "Sword of Bob", "D+6 (Adds +2 Fire)")
        val testWeapon3 = WeaponItemData((R.drawable.ic_armor), "Stick", "D+2")
        val testWeapon4 = WeaponItemData((R.drawable.ic_armor), "Dagger of Doom", "D+100 (Breaks on contact)")
        val testWeapon5 = WeaponItemData((R.drawable.ic_armor), "Cestus", "D+2 (Uses Unarmed)")

        weaponsListTestArray.add(testWeapon2)
        weaponsListTestArray.add(testWeapon3)
        weaponsListTestArray.add(testWeapon4)
        weaponsListTestArray.add(testWeapon5)
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