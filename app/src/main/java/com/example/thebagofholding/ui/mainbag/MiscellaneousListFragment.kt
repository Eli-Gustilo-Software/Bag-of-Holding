package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.MiscellaneousItemData
import com.example.thebagofholding.MiscellaneousListRecyclerAdapter
import com.example.thebagofholding.R

class MiscellaneousListFragment : Fragment(){
    private lateinit var miscListRecyclerView: RecyclerView
    private var miscListRecyclerAdapter: MiscellaneousListRecyclerAdapter? = null
    private var miscListTestArray = ArrayList<MiscellaneousItemData>()

    init {
        val testWeapon2 = MiscellaneousItemData((R.drawable.ic_armor), "Potion of Health", "D+5")
        val testWeapon3 = MiscellaneousItemData((R.drawable.ic_armor), "Potion of Strength", "D+2")
        val testWeapon4 = MiscellaneousItemData((R.drawable.ic_armor), "Poison", "D+100")
        val testWeapon5 = MiscellaneousItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon6 = MiscellaneousItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon7 = MiscellaneousItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon8 = MiscellaneousItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon9 = MiscellaneousItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon10 = MiscellaneousItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon11 = MiscellaneousItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon12 = MiscellaneousItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")

        miscListTestArray.add(testWeapon2)
        miscListTestArray.add(testWeapon3)
        miscListTestArray.add(testWeapon4)
        miscListTestArray.add(testWeapon5)
        miscListTestArray.add(testWeapon6)
        miscListTestArray.add(testWeapon7)
        miscListTestArray.add(testWeapon8)
        miscListTestArray.add(testWeapon9)
        miscListTestArray.add(testWeapon10)
        miscListTestArray.add(testWeapon11)
        miscListTestArray.add(testWeapon12)
        miscListTestArray.add(testWeapon2)
        miscListTestArray.add(testWeapon3)
        miscListTestArray.add(testWeapon4)
        miscListTestArray.add(testWeapon5)
        miscListTestArray.add(testWeapon6)
        miscListTestArray.add(testWeapon7)
        miscListTestArray.add(testWeapon8)
        miscListTestArray.add(testWeapon9)
        miscListTestArray.add(testWeapon10)
        miscListTestArray.add(testWeapon11)
        miscListTestArray.add(testWeapon12)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_misc_list, container, false)
        miscListRecyclerView = root.findViewById(R.id.misc_list_recyclerView)
        if (miscListRecyclerAdapter == null){ //create it
            miscListRecyclerAdapter = MiscellaneousListRecyclerAdapter(miscListTestArray)
            miscListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
            miscListRecyclerView.adapter = miscListRecyclerAdapter
            miscListRecyclerAdapter!!.notifyDataSetChanged()
        }else{//is created, so update.

        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}