package com.example.thebagofholding.ui.character

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*
import java.util.*
import kotlin.collections.ArrayList


enum class CharacterViewHolderTypes(val typeKey: Int){
    NEW_CHARACTER_BUTTON(1),
    REGULAR_CHARACTER_BUTTON(2)
}

class CharacterCreationRecyclerAdapter(var characterList: ArrayList<CharacterInformation>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class CharacterCreationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "CCViewHolder"
        private val characterCreationButton: ConstraintLayout = view.findViewById(R.id.character_creation_cell_constraint_layout)
        private val context = super.itemView.context
        private lateinit var characterNameInputEditText : EditText
        private lateinit var backButton : Button
        private lateinit var createButton : Button
        private var newCharacterName = ""

        init {
            // Define click listener for the ViewHolder's View.
            characterCreationButton.setOnClickListener(){
                //NEW Character button popup

                Log.d(tag, "newCharacter button clicked")
                val dialog: AlertDialog?
                val builder = AlertDialog.Builder(context)
                // set the custom layout
                val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val view = layoutInflater?.inflate(R.layout.character_creation_screen, null)
                builder.setView(view)
                // create and show the alert dialog
                dialog = builder.create()
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                //Set views
                if (view != null){
                    characterNameInputEditText = view.findViewById(R.id.character_creation_name_entry_edittext)
                    backButton = view.findViewById(R.id.character_creation_screen_back_button)
                    createButton = view.findViewById(R.id.character_creation_screen_create_button)
                }

                //EditText
                characterNameInputEditText.setOnKeyListener { v, keyCode, event ->
                    Log.d(tag, "Keycode = $keyCode")
                    Log.d(tag, "event = $event")
                    Log.d(tag, "v = $v")

                    when {
                        //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                        ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                            characterNameInputEditText.clearFocus()
                            val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                            if (view != null) {
                                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                            }
                            newCharacterName = characterNameInputEditText.text.toString()
                            //return true
                            return@setOnKeyListener true
                        }
                        keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT-> {
                            characterNameInputEditText.clearFocus()
                            val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                            if (view != null) {
                                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                            }
                            newCharacterName = characterNameInputEditText.text.toString()
                            //return true
                            return@setOnKeyListener true
                        }
                        else -> false
                    }
                }

                //Buttons
                backButton.setOnClickListener(){
                    dialog.dismiss()
                }
                createButton.setOnClickListener(){
                    //Ensure the name is a valid, goodish name.
                    if (newCharacterName == ""){
                        Toast.makeText(context, "Please hit enter.", Toast.LENGTH_LONG).show()
                    }else{
                        Log.d(tag, "characterSaved is $newCharacterName")
                        DataMaster.saveCharacterInformation(CharacterInformation(newCharacterName, ArrayList(), ArrayList(), ArrayList(), ArrayList(), CharacterPurseData("0", "0", "0", "0"), UUID.randomUUID()))
                        notifyDataSetChanged()
                        dialog.dismiss()
                    }
                }
            }
        }
    }

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "CharacterViewHolder"
        val characterNameTextView: TextView = view.findViewById(R.id.character_name_cell_textview)
        private val characterNameCell : ConstraintLayout = view.findViewById(R.id.character_creation_mother_constraintlayout)
        private val context = super.itemView.context
        private lateinit var characterDeletionDoublecheckTextView : TextView
        private lateinit var backButton : Button
        private lateinit var confirmButton : Button
        lateinit var characterInformation : CharacterInformation

        init {
            // Define click listener for the ViewHolder's View.

            characterNameCell.setOnLongClickListener(){
                val popupMenu= PopupMenu(view.context, it) //TODO need to move this to the right of the screen.
                popupMenu.inflate(R.menu.character_confirmation_window)
                popupMenu.setOnMenuItemClickListener { item->
                    when(item.itemId)
                    {
                        R.id.change_character_confirmation_window_confirm -> {
                            Log.d(tag, "characterNameCell clicked ${characterNameTextView.text}")
                            val v = getSystemService(context, Vibrator::class.java)
                            if (Build.VERSION.SDK_INT >= 26) {
                                v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                            } else {
                                v?.vibrate(200)
                            }
                            Toast.makeText(context, "Character changed to ${characterNameTextView.text}", Toast.LENGTH_SHORT).show()
                            DataMaster.changeCharacter(characterNameTextView.text.toString())
                        }

                        R.id.delete_character -> {
                            Log.d(tag, "deleted_character clicked on ${characterNameTextView.text}")
                            val dialog: AlertDialog?
                            val builder = AlertDialog.Builder(context)
                            // set the custom layout
                            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                            val view = layoutInflater?.inflate(R.layout.character_deletion_doublecheck_dialog, null)
                            builder.setView(view)
                            // create and show the alert dialog
                            dialog = builder.create()
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog.show()

                            //Set views
                            if (view != null) {
                                characterDeletionDoublecheckTextView = view.findViewById(R.id.deletion_doublecheck_textview)
                                characterDeletionDoublecheckTextView.text = "Please confirm that you wish to delete ${characterNameTextView.text}. Note this is irreversible and the character will be gone forever."
                                backButton = view.findViewById(R.id.deletion_back)
                                confirmButton = view.findViewById(R.id.deletion_confirm)
                            }

                            //Buttons
                            backButton.setOnClickListener() {
                                dialog.dismiss()
                            }
                            confirmButton.setOnClickListener() {
                                DataMaster.deleteCharacter(characterInformation)
                                dialog.dismiss()
                            }
                        }
                    }
                    true
                }
                popupMenu.show()
                true
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Create a new view, which defines the UI of the list item
        if (viewType == CharacterViewHolderTypes.REGULAR_CHARACTER_BUTTON.typeKey){
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.character_recycler_cell, viewGroup, false)
            return CharacterViewHolder(view)
        }else{
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.character_creation_new_character_button_cell, viewGroup, false)
            return CharacterCreationViewHolder(view)
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position < characterList.size){
            //Character is found, display them.
            val characterNameHolder = holder as CharacterViewHolder
            val characterName = characterList[position].characterName
            characterNameHolder.characterNameTextView.text = characterName
            characterNameHolder.characterInformation = characterList[position]
        }else{
            //Final position is a new character button.
            val characterNameHolder = holder as CharacterCreationViewHolder
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        //return plus one to add the addCharacterButton
        return characterList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position < characterList.size){
            return CharacterViewHolderTypes.REGULAR_CHARACTER_BUTTON.typeKey
        }else{
            return CharacterViewHolderTypes.NEW_CHARACTER_BUTTON.typeKey
        }
    }

    fun updateData(characterInformationList: ArrayList<CharacterInformation>){
        characterList = characterInformationList
        notifyDataSetChanged()
    }
}
