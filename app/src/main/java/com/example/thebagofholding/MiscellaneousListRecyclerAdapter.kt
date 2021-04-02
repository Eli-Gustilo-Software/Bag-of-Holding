package com.example.thebagofholding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class MiscellaneousItemData (
    val miscImage: Int,
    val miscName: String,
    val miscEffectsOne: String
        )

class MiscellaneousListRecyclerAdapter (var miscList: ArrayList<MiscellaneousItemData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MiscellaneousItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val miscNameTextView: TextView = view.findViewById(R.id.misc_cell_name_textview)
        val miscImageImageView : ImageView = view.findViewById(R.id.misc_cell_imageview)
        val miscEffectsOneTextView : TextView = view.findViewById(R.id.misc_cell_effect1_textview)

        init {
            // Define click listener for the ViewHolder's View.
            miscNameTextView.text = "Testing this is where this is set"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.misc_recycler_cell, parent, false)
        return MiscellaneousItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO figure out how to access a data class properties from here
        val miscNameHolder = holder as MiscellaneousItemViewHolder

        miscNameHolder.miscNameTextView.text = miscList[position].miscName
        miscNameHolder.miscImageImageView.setBackgroundResource(miscList[position].miscImage)
        miscNameHolder.miscEffectsOneTextView.text = miscList[position].miscEffectsOne
    }

    override fun getItemCount(): Int {
        return miscList.size
    }
}