package com.example.thebagofholding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterCreationRecyclerAdapter (var characterList: ArrayList<CharacterInformation>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class CCViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val characterNameTextView: TextView = view.findViewById(R.id.character_creation_edittext_name_cell)

        init {
            // Define click listener for the ViewHolder's View.
            characterNameTextView.setOnClickListener(){
                //TODO make this a singleton thing
                val dataMaster = DataMaster(super.itemView.context)
                val characterToBeSaved = CharacterInformation(
                    characterNameTextView.text.toString(),
                    ArrayList(),
                    ArrayList(),
                    ArrayList(),
                    ArrayList()
                )
                dataMaster.saveCharacterInformation(characterToBeSaved)
            }
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
        val characterName = characterList[position].characterName
        characterNameHolder.characterNameTextView.text = characterName
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {

        //TODO I can make an if check to see if the characterList size is zero and in that case add a newCharacter button. Is that goosd?
        //TODO I want to make it so there is always the newCharacter banner at the bottom. I could use the getItemViewType override.....i want a smarter way.

        return characterList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}
