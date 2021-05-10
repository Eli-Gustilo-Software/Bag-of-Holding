package com.example.thebagofholding.ui.purse

import android.app.Activity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.CharacterPurseData
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.R
import org.w3c.dom.Text


class PurseFragment : Fragment() {
    private lateinit var purseViewModel: PurseViewModel
    private var TAG = "PurseFragment"
    private lateinit var currentCharacter : CharacterInformation
    private var currentCharacterPurse = CharacterPurseData("0","0","0","0")

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!//TODO is this current character infromation?? Rename?
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



        purseViewModel = ViewModelProvider(this).get(PurseViewModel::class.java)
        purseViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Views and Listeners and Edit Texts //TODO I could  make it so the initial zero is deleted on new characters on click.
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
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    currentCharacterPurse.bronze = bronzeAmountEditText.text.toString()
                    Log.d(TAG, "new bronze coin amount = ${currentCharacterPurse.silver}")
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                    bronzeAmountEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    currentCharacterPurse.bronze = bronzeAmountEditText.text.toString()
                    Log.d(TAG, "new bronze coin amount = ${currentCharacterPurse.silver}")
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
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
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    currentCharacterPurse.silver = silverAmountEditText.text.toString()
                    Log.d(TAG, "new bronze coin amount = ${currentCharacterPurse.silver}")
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                    silverAmountEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    currentCharacterPurse.silver = silverAmountEditText.text.toString()
                    Log.d(TAG, "new bronze coin amount = ${currentCharacterPurse.silver}")
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
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
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    currentCharacterPurse.gold = goldAmountEditText.text.toString()
                    Log.d(TAG, "new bronze coin amount = ${currentCharacterPurse.gold}")
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                    goldAmountEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    currentCharacterPurse.gold = goldAmountEditText.text.toString()
                    Log.d(TAG, "new bronze coin amount = ${currentCharacterPurse.gold}")
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        //TRUE SILVER
        val truesilverEditText = view.findViewById<TextView>(R.id.purse_money_4_4_amount_edittext) //TODO if i turn this into a EditText it causes trouble. WHY??

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
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    currentCharacterPurse.truesilver = truesilverEditText.text.toString()
                    Log.d(TAG, "new bronze coin amount = ${currentCharacterPurse.truesilver}")
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                    truesilverEditText.clearFocus()
                    val inputMethodManager = this.requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    if (view != null) {
                        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    currentCharacterPurse.truesilver = truesilverEditText.text.toString()
                    Log.d(TAG, "new bronze coin amount = ${currentCharacterPurse.truesilver}")
                    DataMaster.saveMoney(currentCharacter, currentCharacterPurse)
                    //return true
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        //TODO can this ever be null and break???
        bronzeAmountEditText?.text = currentCharacter.characterPurseData.bronze
        silverAmountEditText?.text = currentCharacter.characterPurseData.silver
        goldAmountEditText?.text = currentCharacter.characterPurseData.gold
        truesilverEditText?.text = currentCharacter.characterPurseData.truesilver
    }
}