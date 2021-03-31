package com.example.thebagofholding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterCreationRecyclerAdapter (var characterList: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class CCViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val characterNameTextView: TextView = view.findViewById(R.id.character_creation_textview_name_cell)

        init {
            // Define click listener for the ViewHolder's View.
            characterNameTextView.text = "Testing this is where this is set"
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CCViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.character_creation_recycler_cell, viewGroup, false)
        return CCViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val characterNameHolder = holder as CCViewHolder
        val characterName = characterList[position]
        characterNameHolder.characterNameTextView.text = characterName
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        //TODO should i use .count instead? What is the difference?
        return characterList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}
