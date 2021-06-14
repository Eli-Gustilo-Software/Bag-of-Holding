package com.eligustilosoftware.thebagofholding.ui.campfire


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.eligustilosoftware.thebagofholding.*
import java.util.*


class CampfireRecyclerAdapter(private var otherPlayersList: ArrayList<OtherPlayerCharacterInformation>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    @SuppressLint("SetTextI18n")
    class CampfireOtherPlayersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "CampfireOtherPlayersViewHolder"
        private val context = super.itemView.context
        private val otherPlayerCellConstraintLayout : ConstraintLayout = view.findViewById(R.id.character_creation_mother_constraintlayout)
        private lateinit var whisperInputEditText : EditText
        private lateinit var whisperTitleTextview : TextView
        private lateinit var backButton : Button
        private lateinit var sendButton : Button
        private var newWhisper = ""
        lateinit var otherPlayerCharacterInformation : OtherPlayerCharacterInformation

        val otherPlayerNameTextView: TextView = view.findViewById(R.id.character_name_cell_textview)
        init {
            otherPlayerCellConstraintLayout.setOnLongClickListener {
                val wrapper: Context = ContextThemeWrapper(view.context, R.style.PoppupMenu)
                val popupMenu = PopupMenu(wrapper, view)
                popupMenu.inflate(R.menu.campfire_interactions_popup_menu)
                popupMenu.setOnMenuItemClickListener { item->
                    when(item.itemId)
                    {
                        R.id.campfire_whisper_button -> {
                            Log.d(tag, "campfire whisper was called to ${otherPlayerNameTextView.text}")
                            val dialog: AlertDialog?
                            val builder = AlertDialog.Builder(context)
                            // set the custom layout
                            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                            val view = layoutInflater?.inflate(R.layout.campfire_whisper_dialog, null)
                            builder.setView(view)
                            // create and show the alert dialog
                            dialog = builder.create()
                            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            dialog.show()

                            //Set views
                            if (view != null) {
                                whisperInputEditText = view.findViewById(R.id.your_whisper_edittext)
                                whisperTitleTextview = view.findViewById(R.id.other_character_whispering_to_name)
                                backButton = view.findViewById(R.id.deletion_back)
                                sendButton = view.findViewById(R.id.deletion_confirm)
                            }

                            //TEXTVIEW Title
                            whisperTitleTextview.text = "${context.getString(R.string.whisper_dialog_text_1)}${otherPlayerCharacterInformation.otherPlayerCharacterName}?"

                            //EditText
                            whisperInputEditText.addTextChangedListener(object : TextWatcher {
                                override fun afterTextChanged(s: Editable?) {
                                    newWhisper = whisperInputEditText.text.toString()
                                }

                                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                }

                                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                    newWhisper = whisperInputEditText.text.toString()
                                }
                            })
                            whisperInputEditText.setOnKeyListener { v, keyCode, event ->
                                Log.d(tag, "Keycode = $keyCode")
                                Log.d(tag, "event = $event")
                                Log.d(tag, "v = $v")

                                when {
                                    //Check if it is the Enter-Key,      Check if the Enter Key was pressed down
                                    ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                                        whisperInputEditText.clearFocus()
                                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                        if (view != null) {
                                            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                                        }
                                        newWhisper = whisperInputEditText.text.toString()
                                        //return true
                                        return@setOnKeyListener true
                                    }
                                    keyCode == KeyEvent.KEYCODE_NAVIGATE_OUT -> {
                                        whisperInputEditText.clearFocus()
                                        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                        if (view != null) {
                                            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                                        }
                                        newWhisper = whisperInputEditText.text.toString()
                                        //return true
                                        return@setOnKeyListener true
                                    }
                                    else -> false
                                }
                            }
                            //Buttons
                            backButton.setOnClickListener {
                                dialog.dismiss()
                            }
                            sendButton.setOnClickListener {
                                //Ensure the name is a valid, goodish name.
                                if (newWhisper == "") {
                                    Toast.makeText(context, "Please hit enter.", Toast.LENGTH_LONG).show()
                                } else {
                                    Log.d(tag, "newWhisper is $newWhisper")
                                    if (DataMaster.retrieveCharacterInformation() != null) {
                                        val currentCharacter = DataMaster.retrieveCharacterInformation()
                                        DataMaster.sendWhisperToPlayer(currentCharacter!!, otherPlayerCharacterInformation, newWhisper)
                                    }
                                    dialog.dismiss()
                                }
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

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.character_recycler_cell, viewGroup, false)
        return CampfireOtherPlayersViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nameHolder = holder as CampfireOtherPlayersViewHolder
        val otherPlayer = otherPlayersList[position]
        nameHolder.otherPlayerNameTextView.text = otherPlayer.otherPlayerCharacterName
        nameHolder.otherPlayerCharacterInformation = OtherPlayerCharacterInformation(otherPlayer.otherPlayerCharacterName, otherPlayer.otherPlayerCharacterUUID, otherPlayer.otherPlayerDeviceName)
    }

    override fun getItemCount(): Int {
        return otherPlayersList.size
    }

    fun updateData(characterInformationList: ArrayList<OtherPlayerCharacterInformation>){
        otherPlayersList = characterInformationList
        notifyDataSetChanged()
    }
}
