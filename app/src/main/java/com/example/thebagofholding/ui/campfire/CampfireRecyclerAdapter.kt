package com.example.thebagofholding.ui.campfire


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.OtherPlayerCharacterInformation
import com.example.thebagofholding.R
import kotlin.collections.ArrayList

class CampfireRecyclerAdapter (var otherPlayersList: ArrayList<OtherPlayerCharacterInformation>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class CampfireOtherPlayersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "CampfireOtherPlayersViewHolder"
        private val context = super.itemView.context
        val nameTextView = view.findViewById<TextView>(R.id.character_name_cell_textview)
        init {
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.character_recycler_cell, viewGroup, false)
        return CampfireOtherPlayersViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nameHolder = holder as CampfireOtherPlayersViewHolder
        for (item in otherPlayersList){
            nameHolder.nameTextView.text = item.otherPlayerCharacterName
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
