package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.ConsumablesItemData
import com.example.thebagofholding.ConsumablesListRecyclerAdapter
import com.example.thebagofholding.MiscellaneousItemData
import com.example.thebagofholding.R

class ConsumablesListFragment : Fragment(){
    private lateinit var consumablesListRecyclerView: RecyclerView
    private var consumablesListRecyclerAdapter: ConsumablesListRecyclerAdapter? = null
    private var consumablesListTestArray = ArrayList<ConsumablesItemData>()

    init {
        val testWeapon2 = ConsumablesItemData((R.drawable.ic_armor), "Potion of Health", "D+5")
        val testWeapon3 = ConsumablesItemData((R.drawable.ic_armor), "Potion of Strength", "D+2")
        val testWeapon4 = ConsumablesItemData((R.drawable.ic_armor), "Poison", "D+100")
        val testWeapon5 = ConsumablesItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon6 = ConsumablesItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon7 = ConsumablesItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon8 = ConsumablesItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon9 = ConsumablesItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon10 = ConsumablesItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon11 = ConsumablesItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon12 = ConsumablesItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")

        consumablesListTestArray.add(testWeapon2)
        consumablesListTestArray.add(testWeapon3)
        consumablesListTestArray.add(testWeapon4)
        consumablesListTestArray.add(testWeapon5)
        consumablesListTestArray.add(testWeapon6)
        consumablesListTestArray.add(testWeapon7)
        consumablesListTestArray.add(testWeapon8)
        consumablesListTestArray.add(testWeapon9)
        consumablesListTestArray.add(testWeapon10)
        consumablesListTestArray.add(testWeapon11)
        consumablesListTestArray.add(testWeapon12)
        consumablesListTestArray.add(testWeapon2)
        consumablesListTestArray.add(testWeapon3)
        consumablesListTestArray.add(testWeapon4)
        consumablesListTestArray.add(testWeapon5)
        consumablesListTestArray.add(testWeapon6)
        consumablesListTestArray.add(testWeapon7)
        consumablesListTestArray.add(testWeapon8)
        consumablesListTestArray.add(testWeapon9)
        consumablesListTestArray.add(testWeapon10)
        consumablesListTestArray.add(testWeapon11)
        consumablesListTestArray.add(testWeapon12)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_consumables_list, container, false)
        consumablesListRecyclerView = root.findViewById(R.id.consumables_list_recyclerView)
        if (consumablesListRecyclerAdapter == null){ //create it
            consumablesListRecyclerAdapter = ConsumablesListRecyclerAdapter(consumablesListTestArray)
            consumablesListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
            consumablesListRecyclerView.adapter = consumablesListRecyclerAdapter
            consumablesListRecyclerAdapter!!.notifyDataSetChanged()
        }else{//is created, so update.

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}