package com.example.thebagofholding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MiscellaneousListRecyclerAdapter (var miscList: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MiscellaneousViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val miscNameTextView: TextView = view.findViewById(R.id.misc_cell_name_textview)

        init {
            // Define click listener for the ViewHolder's View.
            miscNameTextView.text = "Testing this is where this is set"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.misc_recycler_cell, parent, false)
        return MiscellaneousViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO figure out how to access a data class properties from here
        val miscNameHolder = holder as MiscellaneousViewHolder
        val miscName = miscList[position]
        miscNameHolder.miscNameTextView.text = miscName
    }

    override fun getItemCount(): Int {
        return miscList.size
    }
}