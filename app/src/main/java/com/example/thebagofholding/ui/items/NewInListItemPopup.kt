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

class NewInListItemPopup(val context: Context, itemType: String) {
    private val tag = "ItemDetailsDialogPopup"
    //New Item Variables
    private var newItemType: String? = itemType
    private var newItemName: String? = null
    private var newItemEffect : String? = null
    private var newItemDescription : String? = null

    fun itemDetailsDialogPopup() {
        val context = context
        val dialog: AlertDialog?
        val builder = AlertDialog.Builder(context)

        // set the custom layout
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val view = layoutInflater?.inflate(R.layout.new_item_lists_dialog_popup, null)
        builder.setView(view)
        // create and show the alert dialog
        dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        if (view != null) {
            //Set views
            val itemImageView: ImageView = view.findViewById(R.id.new_item_imageviewPopUp)
            val itemTypeSpinner: Spinner = view.findViewById(R.id.new_item_type_spinnerPopUp)
            val itemNameEditText: EditText = view.findViewById(R.id.new_item_name_edittextPopUp)
            val itemEffectEditText: EditText = view.findViewById(R.id.new_item_effect_edittextPopUp)
            val itemDescriptionEditText: EditText = view.findViewById(R.id.new_item_description_editextPopUp)
            val itemDetailsBackButton: Button = view.findViewById(R.id.new_item_back_buttonPopUp)
            val itemDetailsConfirmButton: Button = view.findViewById(R.id.new_item_create_buttonPopUp)


            //EditTexts
            itemNameEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    newItemName = itemNameEditText.text.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    newItemName = itemNameEditText.text.toString()
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
                        newItemName = itemNameEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                        itemNameEditText.clearFocus()
                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                        newItemName = itemNameEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    else -> false
                }
            }

            itemEffectEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    newItemEffect = itemEffectEditText.text.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    newItemEffect = itemEffectEditText.text.toString()
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
                        newItemEffect = itemEffectEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                        itemEffectEditText.clearFocus()
                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                        newItemEffect = itemEffectEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    else -> false
                }
            }

            itemDescriptionEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    newItemDescription = itemDescriptionEditText.text.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    newItemDescription = itemDescriptionEditText.text.toString()
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
                        newItemDescription = itemDescriptionEditText.text.toString()
                        //return true
                        return@setOnKeyListener true
                    }
                    keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                        itemDescriptionEditText.clearFocus()
                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                        newItemDescription = itemDescriptionEditText.text.toString()
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
                if (newItemName != null && newItemEffect != null) {//item exists
                    when (newItemType) {//TODO should these be enums
                        "weapon" -> { //Weapon
                            val newWeapon = WeaponItemData((R.drawable.item_sword_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                            val character = DataMaster.retrieveCharacterInformation()
                            if (character != null) { //Null is necessary because characterInformation coming back from Data could be null???
                                DataMaster.saveItemWeapon(character, newWeapon)
                                dialog.dismiss()
                            }
                        }
                        "armor" -> { //Armor/Apparel
                            val newArmor = ArmorItemData((R.drawable.item_helmet_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                            val character = DataMaster.retrieveCharacterInformation()
                            if (character != null) {
                                DataMaster.saveItemArmor(character, newArmor)
                                dialog.dismiss()
                            }
                        }
                        "consumable" -> { //Consumable
                            val newConsumable = ConsumablesItemData((R.drawable.item_potion_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                            val character = DataMaster.retrieveCharacterInformation()
                            if (character != null) {
                                DataMaster.saveItemConsumable(character, newConsumable)
                                dialog.dismiss()
                            }
                        }
                        "misc" -> { //Miscellaneous
                            val newMisc = MiscellaneousItemData((R.drawable.item_misc_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
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
            ArrayAdapter.createFromResource(context, R.array.item_types, android.R.layout.simple_spinner_item).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                itemTypeSpinner.adapter = adapter
            }

            //sets initial value for what type of item is being created depending on the list the UI is on.
            when (newItemType) {//TODO should these be enums
                "weapon" -> { //Weapon
                    itemTypeSpinner.setSelection(0)
                    itemImageView.setImageResource(R.drawable.itemtype_weapons_image)
                    Log.d(tag, "newItem type = $newItemType")
                }
                "armor" -> { //Armor/Apparel
                    itemTypeSpinner.setSelection(1)
                    itemImageView.setImageResource(R.drawable.itemtype_armor_image)
                    Log.d(tag, "newItem type = $newItemType")
                }
                "consumable" -> { //Consumable
                    itemTypeSpinner.setSelection(2)
                    itemImageView.setImageResource(R.drawable.itemtype_potion_image)
                    Log.d(tag, "newItem type = $newItemType")
                }
                "misc" -> { //Miscellaneous
                    itemTypeSpinner.setSelection(3)
                    itemImageView.setImageResource(R.drawable.itemtype_misc_image)
                    Log.d(tag, "newItem type = $newItemType")
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
                            newItemType = "weapon"
                            itemImageView.setImageResource(R.drawable.itemtype_weapons_image)
                            Log.d(tag, "newItem type = $newItemType")
                        }
                        1 -> { //Armor/Apparel
                            newItemType = "armor"
                            itemImageView.setImageResource(R.drawable.itemtype_armor_image)
                            Log.d(tag, "newItem type = $newItemType")
                        }
                        2 -> { //Consumable
                            newItemType = "consumable"
                            itemImageView.setImageResource(R.drawable.itemtype_potion_image)
                            Log.d(tag, "newItem type = $newItemType")
                        }
                        3 -> { //Miscellaneous
                            newItemType = "misc"
                            itemImageView.setImageResource(R.drawable.itemtype_misc_image)
                            Log.d(tag, "newItem type = $newItemType")
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
