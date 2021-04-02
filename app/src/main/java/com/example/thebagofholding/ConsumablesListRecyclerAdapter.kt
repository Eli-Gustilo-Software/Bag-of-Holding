package com.example.thebagofholding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ConsumablesItemData (
    val consumablesImage: Int,
    val consumablesName: String,
    val consumablesEffectsOne: String
)

class ConsumablesListRecyclerAdapter (var consumablesList: ArrayList<ConsumablesItemData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ConsumablesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val consumablesNameTextView: TextView = view.findViewById(R.id.consumables_cell_name_textview)
        val consumablesImageImageView : ImageView = view.findViewById(R.id.consumables_cell_imageview)
        val consumablesEffectsOneTextView : TextView = view.findViewById(R.id.consumables_cell_effect1_textview)

        init {
            // Define click listener for the ViewHolder's View.
            consumablesNameTextView.text = "Testing this is where this is set"
        }
    }

    override fun getItemCount(): Int {
        return consumablesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.consumables_recycler_cell, parent, false)
        return ConsumablesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO figure out how to access a data class properties from here
        val consumableNameHolder = holder as ConsumablesViewHolder

        consumableNameHolder.consumablesNameTextView.text = consumablesList[position].consumablesName
        consumableNameHolder.consumablesImageImageView.setBackgroundResource(consumablesList[position].consumablesImage)
        consumableNameHolder.consumablesEffectsOneTextView.text = consumablesList[position].consumablesEffectsOne
    }
}