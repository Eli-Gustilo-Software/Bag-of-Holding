package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.ArmorItemData
import com.example.thebagofholding.ArmorListRecyclerAdapter
import com.example.thebagofholding.ConsumablesItemData
import com.example.thebagofholding.R

class ArmorListFragment : Fragment(){
    private lateinit var armorListRecyclerView: RecyclerView
    private var armorListRecyclerAdapter: ArmorListRecyclerAdapter? = null
    private var armorListTestArray = ArrayList<ArmorItemData>()

    init {
        val testWeapon2 = ArmorItemData((R.drawable.ic_armor), "Potion of Health", "D+5")
        val testWeapon3 = ArmorItemData((R.drawable.ic_armor), "Potion of Strength", "D+2")
        val testWeapon4 = ArmorItemData((R.drawable.ic_armor), "Poison", "D+100")
        val testWeapon5 = ArmorItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon6 = ArmorItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon7 = ArmorItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon8 = ArmorItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon9 = ArmorItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon10 = ArmorItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon11 = ArmorItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")
        val testWeapon12 = ArmorItemData((R.drawable.ic_armor), "Rune of MageWind", "Rank 10 Mage Wind Spell")

        armorListTestArray.add(testWeapon2)
        armorListTestArray.add(testWeapon3)
        armorListTestArray.add(testWeapon4)
        armorListTestArray.add(testWeapon5)
        armorListTestArray.add(testWeapon6)
        armorListTestArray.add(testWeapon7)
        armorListTestArray.add(testWeapon8)
        armorListTestArray.add(testWeapon9)
        armorListTestArray.add(testWeapon10)
        armorListTestArray.add(testWeapon11)
        armorListTestArray.add(testWeapon12)
        armorListTestArray.add(testWeapon2)
        armorListTestArray.add(testWeapon3)
        armorListTestArray.add(testWeapon4)
        armorListTestArray.add(testWeapon5)
        armorListTestArray.add(testWeapon6)
        armorListTestArray.add(testWeapon7)
        armorListTestArray.add(testWeapon8)
        armorListTestArray.add(testWeapon9)
        armorListTestArray.add(testWeapon10)
        armorListTestArray.add(testWeapon11)
        armorListTestArray.add(testWeapon12)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_armor_apparal_list, container, false)
        armorListRecyclerView = root.findViewById(R.id.armor_list_recyclerView)
        if (armorListRecyclerAdapter == null){ //create it
            armorListRecyclerAdapter = ArmorListRecyclerAdapter(armorListTestArray)
            armorListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
            armorListRecyclerView.adapter = armorListRecyclerAdapter
            armorListRecyclerAdapter!!.notifyDataSetChanged()
        }else{//is created, so update.

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}