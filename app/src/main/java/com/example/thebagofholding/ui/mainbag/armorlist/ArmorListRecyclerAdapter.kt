package com.example.thebagofholding.ui.mainbag.armorlist


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

class ArmorListRecyclerAdapter (var currentCharacter: CharacterInformation) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ArmorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "ArmorViewHolder"
        lateinit var itemArmorData: ArmorItemData
        lateinit var itemCharacterOwner : CharacterInformation
        val armorNameTextView: TextView = view.findViewById(R.id.armor_cell_name_textview)
        val armorImageImageView: ImageView = view.findViewById(R.id.armor_cell_imageview)
        val armorEffectsOneTextView: TextView = view.findViewById(R.id.armor_cell_effect1_textview)
        private val armorCellConstraintLayout : ConstraintLayout = view.findViewById(R.id.armor_cell_constraint_layout)

        init {
            // Define click listener for the ViewHolder's View.
            armorCellConstraintLayout.setOnLongClickListener(){
                val popupMenu= PopupMenu(view.context,it) //TODO need to move this to the right of the screen.
                popupMenu.inflate(R.menu.item_popup_menu)
                popupMenu.setOnMenuItemClickListener {item->
                    when(item.itemId)
                    {
                        R.id.item_popup_menu_delete->{
                            Log.d(tag, "delete menu called.")
                            DataMaster.deleteItemArmor(itemCharacterOwner, itemArmorData)
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
                                        Log.d(tag, "player to transfer item = ${player.otherPlayerCharacterName} and item = ${armorNameTextView.text}")
                                        DataMaster.transferItemArmor(itemCharacterOwner, player, itemArmorData)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.armor_recycler_cell, parent, false)
        return ArmorViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO figure out how to access a data class properties from here
        val armorNameHolder = holder as ArmorViewHolder


        armorNameHolder.armorNameTextView.text = currentCharacter.characterArmorItemsList[position].armorName
        armorNameHolder.armorImageImageView.setBackgroundResource(currentCharacter.characterArmorItemsList[position].armorImage)
        armorNameHolder.armorEffectsOneTextView.text = currentCharacter.characterArmorItemsList[position].armorEffectsOne

        holder.itemArmorData = currentCharacter.characterArmorItemsList[position]
        holder.itemCharacterOwner = currentCharacter
    }

    override fun getItemCount(): Int {
        return currentCharacter.characterArmorItemsList.size
    }

    fun updateData(characterInformation: CharacterInformation){
        currentCharacter = characterInformation
        notifyDataSetChanged()
    }
}