package com.eligustilo.thebagofholding.ui.purse

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.eligustilo.thebagofholding.CharacterInformation
import com.eligustilo.thebagofholding.CharacterPurseData
import com.eligustilo.thebagofholding.DataMaster
import com.eligustilo.thebagofholding.R


class PurseFragment : Fragment() {
    private lateinit var purseViewModel: PurseViewModel
    private var tagPurseFragment = "PurseFragment"
    private lateinit var currentCharacter : CharacterInformation
    private var currentCharacterPurse = CharacterPurseData("0", "0", "0", "0")

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!
            currentCharacterPurse.bronze = currentCharacter.characterPurseData.bronze
            currentCharacterPurse.silver = currentCharacter.characterPurseData.silver
            currentCharacterPurse.gold = currentCharacter.characterPurseData.gold
            currentCharacterPurse.truesilver = currentCharacter.characterPurseData.truesilver
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_purse, container, false)

        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = getString(
            R.string.toolbar_title_coin_pruse
        )

        purseViewModel = ViewModelProvider(this).get(PurseViewModel::class.java)
        purseViewModel.text.observe(viewLifecycleOwner) {
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Views and Listeners and Edit Texts
        //BRONZE


        val bronzeAmountEditText = view.findViewById<TextView>(R.id.purse_money_1_4_amount_edittext)
        //Filter to only allow digits: Phone popup is in XML
        val filterBronze = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isDigit(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        bronzeAmountEditText.filters = arrayOf(filterBronze)

        //remove focus if back or enter is clicked.
        bronzeAmountEditText.setOnKeyListener { v, keyCode, event ->
            Log.d(tag, "Keycode = $keyCode")
            Log.d(tag, "event = $event")
            Log.d(tag, "v = $v")

            when {
                //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    bronzeAmountEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    currentCharacterPurse.bronze = bronzeAmountEditText.text.toString()
                    Log.d(
                        tagPurseFragment,
                        "new bronze coin amount = ${currentCharacterPurse.silver}"
                    )
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                    bronzeAmountEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    currentCharacterPurse.bronze = bronzeAmountEditText.text.toString()
                    Log.d(
                        tagPurseFragment,
                        "new bronze coin amount = ${currentCharacterPurse.silver}"
                    )
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }
        val bronzeImage = view.findViewById<ImageView>(R.id.purse_money_1_4_imageview)
        bronzeImage.setOnClickListener {
            bronzeAmountEditText.requestFocus()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
        //SILVER
        val silverAmountEditText = view.findViewById<TextView>(R.id.purse_money_2_4_amount_edittext)

        //Filter to only allow digits: Phone popup is in XML
        val filterSilver = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isDigit(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        silverAmountEditText.filters = arrayOf(filterSilver)

        //remove focus if back or enter is clicked.
        silverAmountEditText.setOnKeyListener { v, keyCode, event ->
            Log.d(tag, "Keycode = $keyCode")
            Log.d(tag, "event = $event")
            Log.d(tag, "v = $v")
            when {
                //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    silverAmountEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    currentCharacterPurse.silver = silverAmountEditText.text.toString()
                    Log.d(
                        tagPurseFragment,
                        "new bronze coin amount = ${currentCharacterPurse.silver}"
                    )
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                    silverAmountEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    currentCharacterPurse.silver = silverAmountEditText.text.toString()
                    Log.d(
                        tagPurseFragment,
                        "new bronze coin amount = ${currentCharacterPurse.silver}"
                    )
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        val silverImage = view.findViewById<ImageView>(R.id.purse_money_2_4_imageview)
        silverImage.setOnClickListener {
            silverAmountEditText.requestFocus()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
        //GOLD
        val goldAmountEditText = view.findViewById<TextView>(R.id.purse_money_3_4_amount_edittext)

        //Filter to only allow digits: Phone popup is in XML
        val filterGold = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isDigit(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        goldAmountEditText.filters = arrayOf(filterGold)

        //remove focus if back or enter is clicked.
        goldAmountEditText.setOnKeyListener { v, keyCode, event ->
            Log.d(tag, "Keycode = $keyCode")
            Log.d(tag, "event = $event")
            Log.d(tag, "v = $v")
            when {
                //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    goldAmountEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    currentCharacterPurse.gold = goldAmountEditText.text.toString()
                    Log.d(
                        tagPurseFragment,
                        "new bronze coin amount = ${currentCharacterPurse.gold}"
                    )
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                    goldAmountEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    currentCharacterPurse.gold = goldAmountEditText.text.toString()
                    Log.d(
                        tagPurseFragment,
                        "new bronze coin amount = ${currentCharacterPurse.gold}"
                    )
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }
        val goldImage = view.findViewById<ImageView>(R.id.purse_money_3_4_imageview)
        goldImage.setOnClickListener {
            goldAmountEditText.requestFocus()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        //TRUE SILVER
        val truesilverEditText = view.findViewById<TextView>(R.id.purse_money_4_4_amount_edittext) //TODO if i turn this into a EditText it causes trouble. WHY?? It gets a string but wants an editable...

        //Filter to only allow digits: Phone popup is in XML
        val filterTrueSilver = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isDigit(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        truesilverEditText.filters = arrayOf(filterTrueSilver)

        //remove focus if back or enter is clicked.
        truesilverEditText.setOnKeyListener { v, keyCode, event ->
            Log.d(tag, "Keycode = $keyCode")
            Log.d(tag, "event = $event")
            Log.d(tag, "v = $v")
            when {
                //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    truesilverEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    currentCharacterPurse.truesilver = truesilverEditText.text.toString()
                    Log.d(
                        tagPurseFragment,
                        "new bronze coin amount = ${currentCharacterPurse.truesilver}"
                    )
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                    truesilverEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    currentCharacterPurse.truesilver = truesilverEditText.text.toString()
                    Log.d(
                        tagPurseFragment,
                        "new bronze coin amount = ${currentCharacterPurse.truesilver}"
                    )
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        val truesilverImage = view.findViewById<ImageView>(R.id.purse_money_4_4_imageview)
        truesilverImage.setOnClickListener {
            truesilverEditText.requestFocus()
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        bronzeAmountEditText?.text = currentCharacter.characterPurseData.bronze
        silverAmountEditText?.text = currentCharacter.characterPurseData.silver
        goldAmountEditText?.text = currentCharacter.characterPurseData.gold
        truesilverEditText?.text = currentCharacter.characterPurseData.truesilver
    }
}