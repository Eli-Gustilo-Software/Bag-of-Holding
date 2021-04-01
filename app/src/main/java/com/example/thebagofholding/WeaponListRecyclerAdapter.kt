package com.example.thebagofholding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeaponListRecyclerAdapter (var weaponList: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class WeaponsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weaponNameTextView: TextView = view.findViewById(R.id.weapon_cell_name_textview)

        init {
            // Define click listener for the ViewHolder's View.
            weaponNameTextView.text = "Testing this is where this is set"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weapon_recycler_cell, parent, false)
        return WeaponsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO figure out how to access a data class properties from here
        val weaponNameHolder = holder as WeaponsViewHolder
        val weaponName = weaponList[position]
        weaponNameHolder.weaponNameTextView.text = weaponName
    }

    override fun getItemCount(): Int {
        return weaponList.size
    }
}