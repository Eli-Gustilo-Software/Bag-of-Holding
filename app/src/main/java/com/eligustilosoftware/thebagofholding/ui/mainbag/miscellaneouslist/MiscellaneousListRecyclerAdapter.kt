package com.eligustilosoftware.thebagofholding.ui.mainbag.miscellaneouslist

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
import com.eligustilosoftware.thebagofholding.*
import com.eligustilosoftware.thebagofholding.ui.items.ItemDetailsDialogPopup
import java.util.*


class MiscellaneousListRecyclerAdapter (var currentCharacter: CharacterInformation) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MiscellaneousItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "MiscellaneousItemViewHolder"
        lateinit var itemMiscellaneousData: MiscellaneousItemData
        lateinit var itemCharacterOwner : CharacterInformation
        private val context = super.itemView.context
        val miscNameTextView: TextView = view.findViewById(R.id.misc_cell_name_textview)
        val miscImageImageView : ImageView = view.findViewById(R.id.misc_cell_imageview)
        val miscEffectsOneTextView : TextView = view.findViewById(R.id.misc_cell_effect1_textview)
        val miscDescriptionTextView : TextView = view.findViewById(R.id.misc_cell_description_textview)
        private val miscCellConstraintLayout : ConstraintLayout = view.findViewById(R.id.misc_cell_constraintlayout)

        init {
            // Define click listener for the ViewHolder's View.
            miscCellConstraintLayout.setOnLongClickListener {
                val popupMenu= PopupMenu(view.context,it)
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
                            val popupMenuTransfer= PopupMenu(view.context, it)
                            //get list of all other characters found
                            if (DataMaster.findOtherPlayers().size == 0){
                                //no other players found
                                popupMenuTransfer.menu.add("No Nearby Players Found")
                            }else{
                                for (player in DataMaster.findOtherPlayers()){
                                    popupMenuTransfer.menu.add(player.otherPlayerCharacterName).setOnMenuItemClickListener {
                                        //player name clicked
                                        Log.d(tag, "player to transfer item = ${player.otherPlayerCharacterName} and item = ${itemMiscellaneousData.miscName}")
                                        val v = ContextCompat.getSystemService(context, Vibrator::class.java)
                                        if (Build.VERSION.SDK_INT >= 26) {
                                            v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                                        } else {
                                            @Suppress("DEPRECATION")
                                            v?.vibrate(200)
                                        }
                                        Toast.makeText(context, "Transferring ${itemMiscellaneousData.miscName} to ${player.otherPlayerCharacterName}" , Toast.LENGTH_SHORT).show()
                                        DataMaster.transferItemMiscellaneous(player, itemMiscellaneousData)
                                        true
                                    }
                                }
                            }
                            popupMenuTransfer.show()
                        }

                        R.id.item_popup_menu_details -> {
                            val itemDetailsDialogPopup = ItemDetailsDialogPopup()
                            val itemData = GenericItemData(
                                itemMiscellaneousData.miscImage,
                                itemMiscellaneousData.miscName,
                                itemMiscellaneousData.miscEffectsOne,
                                "misc",
                                itemMiscellaneousData.miscDescription,
                                itemMiscellaneousData.miscUUID)
                            itemDetailsDialogPopup.itemDetailsDialogPopup(itemData, context)
                        }

                        R.id.item_popup_menu_duplicate -> {
                            val newMiscItem = MiscellaneousItemData(itemMiscellaneousData.miscImage, itemMiscellaneousData.miscName, itemMiscellaneousData.miscEffectsOne, itemMiscellaneousData.miscDescription, UUID.randomUUID())
                            DataMaster.saveItemMiscellaneous(itemCharacterOwner, newMiscItem)
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
        val miscItemHolder = holder as MiscellaneousItemViewHolder

        miscItemHolder.miscNameTextView.text = currentCharacter.characterMiscellaneousItemList[position].miscName
        miscItemHolder.miscImageImageView.setImageResource(currentCharacter.characterMiscellaneousItemList[position].miscImage)
        miscItemHolder.miscEffectsOneTextView.text = currentCharacter.characterMiscellaneousItemList[position].miscEffectsOne
        miscItemHolder.miscDescriptionTextView.text = currentCharacter.characterMiscellaneousItemList[position].miscDescription

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