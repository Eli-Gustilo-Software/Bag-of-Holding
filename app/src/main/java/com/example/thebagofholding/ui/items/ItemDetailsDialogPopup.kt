package com.example.thebagofholding.ui.items

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.thebagofholding.*
import java.util.*

class ItemDetailsDialogPopup {
    private val tag = "ItemDetailsDialogPopup"
    fun itemDetailsDialogPopup(genericItemData: GenericItemData, context: Context) {
        val dialog: AlertDialog?
        val builder = AlertDialog.Builder(context)
        var itemType: String = genericItemData.itemType
        var itemName: String = genericItemData.name
        var itemEffect: String = genericItemData.effectOne
        var itemDescription: String = genericItemData.itemDescription
        val itemUUID : UUID = genericItemData.UUID

        // set the custom layout
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val view = layoutInflater?.inflate(R.layout.item_details_dialog_popup, null)
        builder.setView(view)
        // create and show the alert dialog
        dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        if (view != null) {
            //Set views
            val itemImageView: ImageView = view.findViewById(R.id.item_details_imageview)
            val itemTypeSpinner: Spinner = view.findViewById(R.id.item_details_type_spinner)
            val itemNameEditText: EditText = view.findViewById(R.id.item_details_name_edittext)
            val itemEffectEditText: EditText = view.findViewById(R.id.item_details_effect_edittext)
            val itemDescriptionEditText: EditText = view.findViewById(R.id.item_details_description_editext)
            val itemDetailsBackButton: Button = view.findViewById(R.id.item_details_back_button)
            val itemDetailsConfirmButton: Button = view.findViewById(R.id.item_details_confirm_button)

            //init
            itemNameEditText.setText(genericItemData.name)
            itemEffectEditText.setText(genericItemData.effectOne)
            itemDescriptionEditText.setText(genericItemData.itemDescription)
            itemImageView.setImageResource(genericItemData.image)


            //EditTexts
            itemNameEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    itemName = itemNameEditText.text.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    itemName = itemNameEditText.text.toString()
                }
            })
            itemNameEditText.setOnKeyListener { v, keyCode, event ->
                Log.d(tag, "Keycode = $keyCode")
                Log.d(tag, "event = $event")
                Log.d(tag, "v = $v")

                when {
                    //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                    ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                        itemNameEditText.clearFocus()
                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                        itemName = itemNameEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                        itemNameEditText.clearFocus()
                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                        itemName = itemNameEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    else -> false
                }
            }

            itemEffectEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    itemEffect = itemEffectEditText.text.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    itemEffect = itemEffectEditText.text.toString()
                }
            })
            itemEffectEditText.setOnKeyListener { v, keyCode, event ->
                Log.d(tag, "Keycode = $keyCode")
                Log.d(tag, "event = $event")
                Log.d(tag, "v = $v")

                when {
                    //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                    ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                        itemEffectEditText.clearFocus()
                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                        itemEffect = itemEffectEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                        itemEffectEditText.clearFocus()
                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                        itemEffect = itemEffectEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    else -> false
                }
            }

            itemDescriptionEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    itemDescription = itemDescriptionEditText.text.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    itemDescription = itemDescriptionEditText.text.toString()
                }
            })
            itemDescriptionEditText.setOnKeyListener { v, keyCode, event ->
                Log.d(tag, "Keycode = $keyCode")
                Log.d(tag, "event = $event")
                Log.d(tag, "v = $v")

                when {
                    //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                    ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                        itemDescriptionEditText.clearFocus()
                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                        itemDescription = itemDescriptionEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                        itemDescriptionEditText.clearFocus()
                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                        itemDescription = itemDescriptionEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    else -> false
                }
            }


            //Buttons
            itemDetailsBackButton.setOnClickListener {
                dialog.dismiss()
            }

            itemDetailsConfirmButton.setOnClickListener {
                if (itemName != null && itemEffect != null && itemType != null) {//item exists
                    when (itemType) {//TODO should these be enums
                        "weapon" -> { //Weapon
                            val newWeapon = WeaponItemData((R.drawable.item_sword_icon), itemName, itemEffect, itemDescription, itemUUID)
                            val character = DataMaster.retrieveCharacterInformation()
                            if (character != null) { //Null is necessary because characterInformation coming back from Data could be null???
                                DataMaster.saveItemWeapon(character, newWeapon)
                                dialog.dismiss()
                            }
                        }
                        "armor" -> { //Armor/Apparel
                            val newArmor = ArmorItemData((R.drawable.item_helmet_icon), itemName, itemEffect, itemDescription, itemUUID)
                            val character = DataMaster.retrieveCharacterInformation()
                            if (character != null) {
                                DataMaster.saveItemArmor(character, newArmor)
                                dialog.dismiss()
                            }
                        }
                        "consumable" -> { //Consumable
                            val newConsumable = ConsumablesItemData((R.drawable.item_potion_icon), itemName, itemEffect, itemDescription, itemUUID)
                            val character = DataMaster.retrieveCharacterInformation()
                            if (character != null) {
                                DataMaster.saveItemConsumable(character, newConsumable)
                                dialog.dismiss()
                            }
                        }
                        "misc" -> { //Miscellaneous
                            val newMisc = MiscellaneousItemData((R.drawable.item_misc_icon), itemName, itemEffect, itemDescription, itemUUID)
                            val character = DataMaster.retrieveCharacterInformation()
                            if (character != null) {
                                DataMaster.saveItemMiscellaneous(character, newMisc)
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }

            //SPINNER
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(context, R.array.item_types, R.layout.item_details_spinner).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.item_details_spinner_items)
                // Apply the adapter to the spinner
                itemTypeSpinner.adapter = adapter
            }

            when (genericItemData.itemType) {
                "weapon" -> {
                    itemTypeSpinner.setSelection(0)
                }
                "armor" -> {
                    itemTypeSpinner.setSelection(1)
                }
                "consumable" -> {
                    itemTypeSpinner.setSelection(2)
                }
                "misc" -> {
                    itemTypeSpinner.setSelection(3)
                }
            }

            itemTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                ) {
                    when (position) {//TODO should these be enums
                        0 -> { //Weapon
                            itemType = "weapon"
                            itemImageView.setImageResource(R.drawable.item_sword_icon)
                            Log.d(tag, "newItem type = $itemType")
                        }
                        1 -> { //Armor/Apparel
                            itemType = "armor"
                            itemImageView.setImageResource(R.drawable.item_helmet_icon)
                            Log.d(tag, "newItem type = $itemType")
                        }
                        2 -> { //Consumable
                            itemType = "consumable"
                            itemImageView.setImageResource(R.drawable.item_potion_icon)
                            Log.d(tag, "newItem type = $itemType")
                        }
                        3 -> { //Miscellaneous
                            itemType = "misc"
                            itemImageView.setImageResource(R.drawable.item_misc_icon)
                            Log.d(tag, "newItem type = $itemType")
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //What to do?
                }
            }
        }
    }
}
