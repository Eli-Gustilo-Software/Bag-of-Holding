package com.example.thebagofholding.ui.mainbag.armorlist


import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*

class ArmorListRecyclerAdapter (var currentCharacter: CharacterInformation) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ArmorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "ArmorViewHolder"
        lateinit var itemArmorData: ArmorItemData
        lateinit var itemCharacterOwner : CharacterInformation
        private val context = super.itemView.context
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
                                        val v = ContextCompat.getSystemService(context, Vibrator::class.java)
                                        if (Build.VERSION.SDK_INT >= 26) {
                                            v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                                        } else {
                                            v?.vibrate(200)
                                        }
                                        Toast.makeText(context, "Transferring ${itemArmorData.armorName} to ${player.otherPlayerCharacterName}" , Toast.LENGTH_SHORT).show()
                                        DataMaster.transferItemArmor(player, itemArmorData)
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