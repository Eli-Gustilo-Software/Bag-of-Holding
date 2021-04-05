package com.example.thebagofholding.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.thebagofholding.*

class NewItemFragment : Fragment() {
    private lateinit var newItemViewModel: NewItemViewModel
    private lateinit var backButton : Button
    private lateinit var createButton : Button

    private var armorListTestArray = ArrayList<ArmorItemData>()
    private var consumablesListTestArray = ArrayList<ConsumablesItemData>()
    private var miscListTestArray = ArrayList<MiscellaneousItemData>()
    private var weaponsListTestArray = ArrayList<WeaponItemData>()

    init {
        val testWeapon2 = ArmorItemData((R.drawable.ic_armor), "Potion of Health", "D+5")
        val testWeapon3 = ConsumablesItemData((R.drawable.ic_armor), "Potion of Strength", "D+2")
        val testWeapon4 = MiscellaneousItemData((R.drawable.ic_armor), "Poison", "D+100")
        val testWeapon5 = WeaponItemData((R.drawable.ic_armor), "Cestus", "D+2 (Uses Unarmed)")

        armorListTestArray.add(testWeapon2)
        consumablesListTestArray.add(testWeapon3)
        miscListTestArray.add(testWeapon4)
        weaponsListTestArray.add(testWeapon5)

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        newItemViewModel =
                ViewModelProvider(this).get(NewItemViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new_item, container, false)

        newItemViewModel.text.observe(viewLifecycleOwner, Observer {
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Buttons
        backButton = view.findViewById(R.id.new_item_back_button)
        createButton = view.findViewById(R.id.new_item_create_button)

        super.onViewCreated(view, savedInstanceState)

        backButton.setOnClickListener(){
            val testCharacter = CharacterInformation("TestCharacterName", armorListTestArray, weaponsListTestArray, consumablesListTestArray, miscListTestArray)
            DataMaster.saveCharacterInformation(testCharacter)
        }

        createButton.setOnClickListener(){
            DataMaster.retrieveCharacterInformation()
        }
    }
}