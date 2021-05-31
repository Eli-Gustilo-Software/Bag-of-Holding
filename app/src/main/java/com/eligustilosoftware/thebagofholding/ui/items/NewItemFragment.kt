package com.eligustilosoftware.thebagofholding.ui.items

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eligustilosoftware.thebagofholding.*
import java.util.*


class NewItemFragment : Fragment() {
    private val tagNewItemFrag = "NewItemFragment"
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = getString(
                    R.string.toolbar_title_item_creation)

        newItemViewModel =
            ViewModelProvider(this).get(NewItemViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new_item, container, false)

        newItemViewModel.text.observe(viewLifecycleOwner, {
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
        newItemNameEditText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus: Boolean ->
            newItemName = newItemNameEditText.text.toString()
            Log.d(tagNewItemFrag, "NewItem Name = $newItemName")
        }
        newItemNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                newItemName = newItemNameEditText.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newItemName = newItemNameEditText.text.toString()
            }
        })

        newItemEffectEditText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            newItemEffect = newItemEffectEditText.text.toString()
            Log.d(tagNewItemFrag, "NewItem Effect = $newItemEffect")
        }
        newItemEffectEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                newItemEffect = newItemEffectEditText.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newItemEffect = newItemEffectEditText.text.toString()
            }
        })

        newItemDescriptionEditText.setOnKeyListener { v, keyCode, event ->
            Log.d(tag, "Keycode = $keyCode")
            Log.d(tag, "event = $event")
            Log.d(tag, "v = $v")

            when {
                //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    newItemDescriptionEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    newItemDescription = newItemDescriptionEditText.text.toString()
                    Log.d(tagNewItemFrag, "newItem Description = $newItemDescription")
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT-> {
                    newItemDescriptionEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    newItemDescription = newItemDescriptionEditText.text.toString()
                    Log.d(tagNewItemFrag, "newItem Description = $newItemDescription")
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }
        newItemDescriptionEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                newItemDescription = newItemDescriptionEditText.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newItemDescription = newItemDescriptionEditText.text.toString()
            }
        })

        //BUTTONS
        backButton.setOnClickListener {
            findNavController().navigate(R.id.navigation_character)
        }

        createButton.setOnClickListener {
            if (newItemName != null && newItemEffect != null && newItemType != null){//item exists
                when (newItemType) {//TODO should these be enums
                    "weapon" -> { //Weapon
                        val newWeapon = WeaponItemData((R.drawable.item_sword_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                        val character = DataMaster.retrieveCharacterInformation()
                        if (character != null) { //Null is necessary because characterInformation coming back from Data could be null???
                            DataMaster.saveItemWeapon(character, newWeapon)
                            Toast.makeText(context, "New Item Created! You now have a new $newItemName!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.navigation_character)
                            val v = ContextCompat.getSystemService(this.requireContext(), Vibrator::class.java)
                            if (Build.VERSION.SDK_INT >= 26) {
                                v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                            } else {
                                @Suppress("DEPRECATION")
                                v?.vibrate(200)
                            }
                        }
                    }
                    "armor" -> { //Armor/Apparel
                        val newArmor = ArmorItemData((R.drawable.item_helmet_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                        val character = DataMaster.retrieveCharacterInformation()
                        if (character != null) {
                            DataMaster.saveItemArmor(character, newArmor)
                            Toast.makeText(context, "New Item Created! You now have a new $newItemName!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.navigation_character)
                            val v = ContextCompat.getSystemService(this.requireContext(), Vibrator::class.java)
                            if (Build.VERSION.SDK_INT >= 26) {
                                v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                            } else {
                                @Suppress("DEPRECATION")
                                v?.vibrate(200)
                            }
                        }
                    }
                    "consumable" -> { //Consumable
                        val newConsumable = ConsumablesItemData((R.drawable.item_potion_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                        val character = DataMaster.retrieveCharacterInformation()
                        if (character != null) {
                            DataMaster.saveItemConsumable(character, newConsumable)
                            Toast.makeText(context, "New Item Created! You now have a new $newItemName!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.navigation_character)
                            val v = ContextCompat.getSystemService(this.requireContext(), Vibrator::class.java)
                            if (Build.VERSION.SDK_INT >= 26) {
                                v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                            } else {
                                @Suppress("DEPRECATION")
                                v?.vibrate(200)
                            }
                        }
                    }
                    "misc" -> { //Miscellaneous
                        val newMisc = MiscellaneousItemData((R.drawable.item_misc_icon), newItemName!!, newItemEffect!!, newItemDescription!!, UUID.randomUUID())
                        val character = DataMaster.retrieveCharacterInformation()
                        if (character != null) {
                            DataMaster.saveItemMiscellaneous(character, newMisc)
                            Toast.makeText(context, "New Item Created! You now have a new $newItemName!", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.navigation_character)
                            val v = ContextCompat.getSystemService(this.requireContext(), Vibrator::class.java)
                            if (Build.VERSION.SDK_INT >= 26) {
                                v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                            } else {
                                @Suppress("DEPRECATION")
                                v?.vibrate(200)
                            }
                        }
                    }
                }
            }
        }

        //SPINNER
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(this.requireContext(), R.array.item_types, R.layout.new_item_spinner).also { adapter ->

        // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_new_item_items)
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
                when (position) {//TODO should these be enums
                    0 -> { //Weapon
                        newItemType = "weapon"
                        newItemImageView.setImageResource(R.drawable.itemtype_weapons_image)
                        Log.d(tagNewItemFrag, "newItem type = $newItemType")
                    }
                    1 -> { //Armor/Apparel
                        newItemType = "armor"
                        newItemImageView.setImageResource(R.drawable.itemtype_armor_image)
                        Log.d(tagNewItemFrag, "newItem type = $newItemType")
                    }
                    2 -> { //Consumable
                        newItemType = "consumable"
                        newItemImageView.setImageResource(R.drawable.itemtype_potion_image)
                        Log.d(tagNewItemFrag, "newItem type = $newItemType")
                    }
                    3 -> { //Miscellaneous
                        newItemType = "misc"
                        newItemImageView.setImageResource(R.drawable.itemtype_misc_image)
                        Log.d(tagNewItemFrag, "newItem type = $newItemType")
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