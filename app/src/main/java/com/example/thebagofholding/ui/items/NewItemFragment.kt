package com.example.thebagofholding.ui.items

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.thebagofholding.*
import java.util.*
import kotlin.collections.ArrayList


class NewItemFragment : Fragment() {
    private val TAG = "NewItemFragment"

    //View/Class Variables
    private lateinit var newItemViewModel: NewItemViewModel
    private lateinit var backButton: Button
    private lateinit var createButton: Button
    private lateinit var spinner: Spinner
    private lateinit var newItemNameEditText: EditText
    private lateinit var newItemEffectEditText: EditText
    private lateinit var newItemDescriptionEditText: EditText
    private lateinit var newItemImageView : ImageView

    //New Item Variables
    private var newItemType: String? = null
    private var newItemName: String? = null
    private var newItemEffect : String? = null
    private var newItemDescription : String? = null

    init {

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

        //Initialize Things
        backButton = view.findViewById(R.id.new_item_back_button)
        createButton = view.findViewById(R.id.new_item_create_button)
        spinner = view.findViewById(R.id.new_item_type_spinner)
        newItemNameEditText = view.findViewById(R.id.new_item_name_edittext)
        newItemEffectEditText = view.findViewById(R.id.new_item_effect_edittext)
        newItemDescriptionEditText = view.findViewById(R.id.new_item_description_editext)
        newItemImageView = view.findViewById(R.id.new_item_imageview)

        super.onViewCreated(view, savedInstanceState)

        //EDIT TEXTS
        newItemNameEditText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            newItemName = newItemNameEditText.text.toString()
            Log.d(TAG, "NewItem Name = $newItemName")
        }
        newItemEffectEditText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            newItemEffect = newItemEffectEditText.text.toString()
            Log.d(TAG, "NewItem Effect = $newItemEffect")
        }
        newItemDescriptionEditText.setOnKeyListener { v, keyCode, event ->
            Log.d(tag, "Keycode = $keyCode")
            Log.d(tag, "event = $event")
            Log.d(tag, "v = $v")

            when {
                //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    newItemDescriptionEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    newItemDescription = newItemDescriptionEditText.text.toString()
                    Log.d(TAG, "newItem Description = $newItemDescription")
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT-> {
                    newItemDescriptionEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    newItemDescription = newItemDescriptionEditText.text.toString()
                    Log.d(TAG, "newItem Description = $newItemDescription")
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        //BUTTONS
        backButton.setOnClickListener(){
            findNavController().navigate(R.id.navigation_character)
        }

        createButton.setOnClickListener(){
            if (newItemName != null && newItemEffect != null && newItemType != null){//item exists
                when (newItemType) {//TODO should these be enums? what is the point of ENUMS?
                    "weapon" -> { //Weapon
                        val newWeapon = WeaponItemData((R.drawable.item_sword_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                        val character = DataMaster.retrieveCharacterInformation()
                        if (character != null) { //Null is necessary because characterInformation coming back from Data could be null???
                            DataMaster.saveItemWeapon(character, newWeapon)
                        }
                    }
                    "armor" -> { //Armor/Apparel
                        val newArmor = ArmorItemData((R.drawable.item_helmet_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                        val character = DataMaster.retrieveCharacterInformation()
                        if (character != null) {
                            DataMaster.saveItemArmor(character, newArmor)
                        }
                    }
                    "consumable" -> { //Consumable
                        val newConsumable = ConsumablesItemData((R.drawable.item_potion_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                        val character = DataMaster.retrieveCharacterInformation()
                        if (character != null) {
                            DataMaster.saveItemConsumable(character, newConsumable)
                        }
                    }
                    "misc" -> { //Miscellaneous
                        val newMisc = MiscellaneousItemData((R.drawable.item_misc_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                        val character = DataMaster.retrieveCharacterInformation()
                        if (character != null) {
                            DataMaster.saveItemMiscellaneous(character, newMisc)
                        }
                    }
                }
            }
        }

        //SPINNER
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(this.requireContext(), R.array.item_types, android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {//TODO should these be enums? what is the point of ENUMS?
                    0 -> { //Weapon
                        newItemType = "weapon"
                        newItemImageView.setBackgroundResource(R.drawable.itemtype_weapons_image) //TODO need to make it so you can click and hold on image and then get a selection screen? Dialog popup with recycler?
                        Log.d(TAG, "newItem type = $newItemType")
                    }
                    1 -> { //Armor/Apparel
                        newItemType = "armor"
                        newItemImageView.setBackgroundResource(R.drawable.itemtype_armor_image) //TODO need to make it so you can click and hold on image and then get a selection screen? Dialog popup with recycler?
                        Log.d(TAG, "newItem type = $newItemType")
                    }
                    2 -> { //Consumable
                        newItemType = "consumable"
                        newItemImageView.setBackgroundResource(R.drawable.itemtype_potion_image) //TODO need to make it so you can click and hold on image and then get a selection screen? Dialog popup with recycler?
                        Log.d(TAG, "newItem type = $newItemType")
                    }
                    3 -> { //Miscellaneous
                        newItemType = "misc"
                        newItemImageView.setBackgroundResource(R.drawable.itemtype_misc_image) //TODO need to make it so you can click and hold on image and then get a selection screen? Dialog popup with recycler?
                        Log.d(TAG, "newItem type = $newItemType")
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //What to do?
            }
        }

        //IMAGE VIEW
        newItemImageView.setBackgroundResource(R.drawable.itemtype_armor_image)
    }
}