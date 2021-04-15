package com.example.thebagofholding.ui.mainbag.miscellaneouslist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*


class MiscellaneousListRecyclerAdapter (var currentCharacter: CharacterInformation) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MiscellaneousItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "MiscellaneousItemViewHolder"
        lateinit var itemMiscellaneousData: MiscellaneousItemData
        lateinit var itemCharacterOwner : CharacterInformation
        val miscNameTextView: TextView = view.findViewById(R.id.misc_cell_name_textview)
        val miscImageImageView : ImageView = view.findViewById(R.id.misc_cell_imageview)
        val miscEffectsOneTextView : TextView = view.findViewById(R.id.misc_cell_effect1_textview)
        private val miscCellConstraintLayout : ConstraintLayout = view.findViewById(R.id.misc_cell_constraintlayout)

        init {
            // Define click listener for the ViewHolder's View.
            miscCellConstraintLayout.setOnLongClickListener(){
                val popupMenu= PopupMenu(view.context,it) //TODO need to move this to the right of the screen.
                popupMenu.inflate(R.menu.item_popup_menu)
                popupMenu.setOnMenuItemClickListener {item->
                    when(item.itemId)
                    {
                        R.id.item_popup_menu_delete->{
                            Log.d(tag, "delete menu called.")
                            DataMaster.deleteItemMiscellaneous(itemCharacterOwner, itemMiscellaneousData)
                        }

                        R.id.item_popup_menu_transfer->{
                            Log.d(tag, "transfer menu called")
                            val popupMenuTransfer= PopupMenu(view.context, it) //TODO need to move this to the right of the screen.
                            //get list of all other characters found
                            if (DataMaster.findOtherPlayers().size == 0){
                                //no other players found
                                popupMenuTransfer.menu.add("No Nearby Players Found")
                            }else{
                                for (player in DataMaster.findOtherPlayers()){
                                    popupMenuTransfer.menu.add(player.otherPlayerCharacterName).setOnMenuItemClickListener {
                                        //player name clicked
                                        Log.d(tag, "player to transfer item = ${player.otherPlayerCharacterName} and item = ${itemMiscellaneousData.miscName}")
                                        DataMaster.transferItemMiscellaneous(itemCharacterOwner, player, itemMiscellaneousData)
                                        true
                                    }
                                }
                            }
                            popupMenuTransfer.show()
                        }
                    }
                    true
                }
                popupMenu.show()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.misc_recycler_cell, parent, false)
        return MiscellaneousItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO figure out how to access a data class properties from here
        val miscNameHolder = holder as MiscellaneousItemViewHolder

        miscNameHolder.miscNameTextView.text = currentCharacter.characterMiscellaneousItemList[position].miscName
        miscNameHolder.miscImageImageView.setBackgroundResource(currentCharacter.characterMiscellaneousItemList[position].miscImage)
        miscNameHolder.miscEffectsOneTextView.text = currentCharacter.characterMiscellaneousItemList[position].miscEffectsOne

        holder.itemMiscellaneousData = currentCharacter.characterMiscellaneousItemList[position]
        holder.itemCharacterOwner = currentCharacter
    }

    override fun getItemCount(): Int {
        return currentCharacter.characterMiscellaneousItemList.size
    }

    fun updateData(characterInformation: CharacterInformation){
        currentCharacter = characterInformation
        notifyDataSetChanged()
    }
}