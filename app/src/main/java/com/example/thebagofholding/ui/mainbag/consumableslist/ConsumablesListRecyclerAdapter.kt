package com.example.thebagofholding.ui.mainbag.consumableslist

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

class ConsumablesListRecyclerAdapter (var currentCharacter: CharacterInformation) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ConsumablesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "ConsumablesViewHolder"
        lateinit var itemConsumablesData: ConsumablesItemData
        lateinit var itemCharacterOwner : CharacterInformation
        private val context = super.itemView.context
        val consumablesNameTextView: TextView = view.findViewById(R.id.consumables_cell_name_textview)
        val consumablesImageImageView : ImageView = view.findViewById(R.id.consumables_cell_imageview)
        val consumablesEffectsOneTextView : TextView = view.findViewById(R.id.consumables_cell_effect1_textview)
        private val consumablesCellConstraintLayout : ConstraintLayout = view.findViewById(R.id.consumables_cell_constraintlayout)

        init {
            // Define click listener for the ViewHolder's View.
            consumablesCellConstraintLayout.setOnLongClickListener(){
                val popupMenu= PopupMenu(view.context,it) //TODO need to move this to the right of the screen.
                popupMenu.inflate(R.menu.item_popup_menu)
                popupMenu.setOnMenuItemClickListener {item->
                    when(item.itemId)
                    {
                        R.id.item_popup_menu_delete->{
                            Log.d(tag, "delete menu called.")
                            DataMaster.deleteItemConsumable(itemCharacterOwner, itemConsumablesData)
                        }

                        R.id.item_popup_menu_transfer->{
                            Log.d(tag, "transfer menu called")
                            val popupMenuTransfer= PopupMenu(view.context, it) //TODO need to move this to the right of the screen.
                            //get list of all other characters found
                            if (DataMaster.findOtherPlayers().size <= 0){
                                //no other players found
                                popupMenuTransfer.menu.add("No Nearby Players Found")
                            }else{
                                for (player in DataMaster.findOtherPlayers()){
                                    popupMenuTransfer.menu.add(player.otherPlayerCharacterName).setOnMenuItemClickListener {
                                        //player name clicked
                                        Log.d(tag, "player to transfer item = ${player.otherPlayerCharacterName} and item = ${consumablesNameTextView.text}")
                                        val v = ContextCompat.getSystemService(context, Vibrator::class.java)
                                        if (Build.VERSION.SDK_INT >= 26) {
                                            v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                                        } else {
                                            v?.vibrate(200)
                                        }
                                        Toast.makeText(context, "Transferring ${itemConsumablesData.consumablesName} to ${player.otherPlayerCharacterName}" , Toast.LENGTH_SHORT).show()
                                        DataMaster.transferItemConsumable(player, itemConsumablesData)
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

    override fun getItemCount(): Int {
        return currentCharacter.characterConsumablesItemsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.consumables_recycler_cell, parent, false)
        return ConsumablesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val consumableNameHolder = holder as ConsumablesViewHolder

        consumableNameHolder.consumablesNameTextView.text = currentCharacter.characterConsumablesItemsList[position].consumablesName
        consumableNameHolder.consumablesImageImageView.setBackgroundResource(currentCharacter.characterConsumablesItemsList[position].consumablesImage)
        consumableNameHolder.consumablesEffectsOneTextView.text = currentCharacter.characterConsumablesItemsList[position].consumablesEffectsOne

        holder.itemConsumablesData = currentCharacter.characterConsumablesItemsList[position]
        holder.itemCharacterOwner = currentCharacter
    }

    fun updateData(characterInformation: CharacterInformation){
        currentCharacter = characterInformation
        notifyDataSetChanged()
    }
}