package com.example.thebagofholding


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ArmorItemData (
    val armorImage: Int,
    val armorName: String,
    val armorEffectsOne: String
)

class ArmorListRecyclerAdapter (var armorList: ArrayList<ArmorItemData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ArmorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val armorNameTextView: TextView = view.findViewById(R.id.armor_cell_name_textview)
        val armorImageImageView: ImageView = view.findViewById(R.id.armor_cell_imageview)
        val armorEffectsOneTextView: TextView = view.findViewById(R.id.armor_cell_effect1_textview)

        init {
            // Define click listener for the ViewHolder's View.
            armorNameTextView.text = "Testing this is where this is set"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.armor_recycler_cell, parent, false)
        return ArmorViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO figure out how to access a data class properties from here
        val armorNameHolder = holder as ArmorViewHolder


        armorNameHolder.armorNameTextView.text = armorList[position].armorName
        armorNameHolder.armorImageImageView.setBackgroundResource(armorList[position].armorImage)
        armorNameHolder.armorEffectsOneTextView.text = armorList[position].armorEffectsOne
    }

    override fun getItemCount(): Int {
        return armorList.size
    }
}