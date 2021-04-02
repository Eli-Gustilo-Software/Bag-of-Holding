package com.example.thebagofholding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class WeaponItemData (
    val image: Int,
    val name: String,
    val weaponEffectOne: String
    )

class WeaponListRecyclerAdapter (var weaponItemList: ArrayList<WeaponItemData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class WeaponsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val weaponNameTextView: TextView = view.findViewById(R.id.weapon_cell_name_textview)
        val weaponImageImageView: ImageView = view.findViewById(R.id.weapon_cell_imageview)
        val weaponEffectOneTextView: TextView = view.findViewById(R.id.weapon_cell_effect1_textview)

        init {
            // Define click listener for the ViewHolder's View.
            weaponNameTextView.text = "Testing this is where this is set"
        }
    }

    override fun getItemCount(): Int {
        return weaponItemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weapon_recycler_cell, parent, false)
        return WeaponsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO figure out how to access a data class properties from here
        val weaponObjectViewHolder = holder as WeaponsViewHolder
        val weaponName = weaponItemList[position].name
        val weaponImage = weaponItemList[position].image
        val weaponEffectOne = weaponItemList[position].weaponEffectOne

        weaponObjectViewHolder.weaponNameTextView.text = weaponName
        weaponObjectViewHolder.weaponImageImageView.setBackgroundResource(weaponImage)
        weaponObjectViewHolder.weaponEffectOneTextView.text = weaponEffectOne
    }


}