package com.example.thebagofholding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ConsumablesListRecyclerAdapter (var consumablesList: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ConsumablesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val consumablesNameTextView: TextView = view.findViewById(R.id.consumables_cell_name_textview)

        init {
            // Define click listener for the ViewHolder's View.
            consumablesNameTextView.text = "Testing this is where this is set"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.consumables_recycler_cell, parent, false)
        return ConsumablesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO figure out how to access a data class properties from here
        val consumableNameHolder = holder as ConsumablesViewHolder
        val consumableName = consumablesList[position]
        consumableNameHolder.consumablesNameTextView.text = consumableName
    }

    override fun getItemCount(): Int {
        return consumablesList.size
    }
}