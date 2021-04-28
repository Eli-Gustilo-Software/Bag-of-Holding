package com.example.thebagofholding.ui.campfire


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.OtherPlayerCharacterInformation
import com.example.thebagofholding.R
import kotlin.collections.ArrayList

class CampfireRecyclerAdapter (var otherPlayersList: ArrayList<OtherPlayerCharacterInformation>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class CampfireOtherPlayersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "CampfireOtherPlayersViewHolder"
        private val context = super.itemView.context
        private val otherPlayerCellConstraintLayout : ConstraintLayout = view.findViewById(R.id.character_creation_mother_constraintlayout)
        val otherPlayerNameTextView: TextView = view.findViewById(R.id.character_name_cell_textview)
        init {
            otherPlayerCellConstraintLayout.setOnLongClickListener(){
                val popupMenu= PopupMenu(view.context,it) //TODO need to move this to the right of the screen.
                popupMenu.inflate(R.menu.campfire_interactions_popup_menu)
                popupMenu.setOnMenuItemClickListener {item->
                    when(item.itemId)
                    {
                        R.id.campfire_whisper_button->{
                            Log.d(tag, "campfire whisper was called to ${otherPlayerNameTextView.text}")
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
        for (item in otherPlayersList){
            nameHolder.otherPlayerNameTextView.text = item.otherPlayerCharacterName
        }
    }

    override fun getItemCount(): Int {
        return otherPlayersList.size
    }

    fun updateData(characterInformationList: ArrayList<OtherPlayerCharacterInformation>){
        otherPlayersList = characterInformationList
        notifyDataSetChanged()
    }
}
