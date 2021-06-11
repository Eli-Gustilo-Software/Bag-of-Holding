package com.eligustilosoftware.thebagofholding.ui.mainbag.armorlist


import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eligustilosoftware.thebagofholding.*
import com.eligustilosoftware.thebagofholding.MainActivity
import com.eligustilosoftware.thebagofholding.ui.items.ItemDetailsDialogPopup
import java.util.*


class ArmorListRecyclerAdapter(var currentCharacter: CharacterInformation, val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ArmorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "ArmorViewHolder"
        lateinit var itemArmorData: ArmorItemData
        lateinit var itemCharacterOwner: CharacterInformation
        private val context = super.itemView.context
        val armorNameTextView: TextView = view.findViewById(R.id.armor_cell_name_textview)
        val armorImageImageView: ImageView = view.findViewById(R.id.armor_cell_imageview)
        val armorEffectsOneTextView: TextView = view.findViewById(R.id.armor_cell_effect1_textview)
        val armorDescriptionTextView : TextView = view.findViewById(R.id.armor_cell_description_textview)
        private val armorCellConstraintLayout: ConstraintLayout = view.findViewById(R.id.armor_cell_constraint_layout)

        init {
            // Define click listener for the ViewHolder's View.
            armorCellConstraintLayout.setOnLongClickListener {
                val popupMenu = PopupMenu(view.context, view)
                popupMenu.menuInflater.inflate(R.menu.item_popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.item_popup_menu_delete -> {
                            Log.d(tag, "delete menu called.")
                            DataMaster.deleteItemArmor(itemCharacterOwner, itemArmorData)
                        }

                        R.id.item_popup_menu_transfer -> {
                            Log.d(tag, "transfer menu called")
                            val popupMenuTransfer = PopupMenu(view.context, view)
                            if (DataMaster.findOtherPlayers().size == 0) {
                                //no other players found
                                popupMenuTransfer.menu.add("No Nearby Players Found")
                            } else {
                                for (player in DataMaster.findOtherPlayers()) {
                                    popupMenuTransfer.menu.add(player.otherPlayerCharacterName).setOnMenuItemClickListener {
                                        transferItemPopupMenu(player, itemArmorData, context)
                                        true
                                    }
                                }
                            }
                            popupMenuTransfer.show()
                        }

                        R.id.item_popup_menu_details -> {
                            val itemDetailsDialogPopup = ItemDetailsDialogPopup()
                            val itemData = GenericItemData(itemArmorData.armorImage, itemArmorData.armorName, itemArmorData.armorEffectsOne, "armor", itemArmorData.armorDescription, itemArmorData.armorUUID)
                            itemDetailsDialogPopup.itemDetailsDialogPopup(itemData, context)
                        }

                        R.id.item_popup_menu_duplicate -> {
                            val newArmorItem = ArmorItemData(itemArmorData.armorImage, itemArmorData.armorName, itemArmorData.armorEffectsOne, itemArmorData.armorDescription, UUID.randomUUID())
                            DataMaster.saveItemArmor(itemCharacterOwner, newArmorItem)
                        }
                    }

                    true
                }
                popupMenu.show()
                true
            }
        }

        private fun transferItemPopupMenu(characterInformation: OtherPlayerCharacterInformation, armorItemData: ArmorItemData, context: Context) {
            val tag = "ArmorViewHolder"
            Log.d(tag, "player to transfer item = ${characterInformation.otherPlayerCharacterName} and item = ${armorItemData.armorName}")
            val v = ContextCompat.getSystemService(context, Vibrator::class.java)
            if (Build.VERSION.SDK_INT >= 26) {
                v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                v?.vibrate(200)
            }
            Toast.makeText(context, "Transferring ${armorItemData.armorName} to ${characterInformation.otherPlayerCharacterName}", Toast.LENGTH_SHORT).show()
            DataMaster.transferItemArmor(characterInformation, armorItemData)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.armor_recycler_cell, parent, false)
        return ArmorViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val armorItemHolder = holder as ArmorViewHolder
        val drawable = context?.getDrawable(R.drawable.item_helmet_icon)


        armorItemHolder.armorNameTextView.text = currentCharacter.characterArmorItemsList[position].armorName

        armorItemHolder.armorImageImageView.setImageResource(currentCharacter.characterArmorItemsList[position].armorImage)
        armorItemHolder.armorEffectsOneTextView.text = currentCharacter.characterArmorItemsList[position].armorEffectsOne
        armorItemHolder.armorDescriptionTextView.text = currentCharacter.characterArmorItemsList[position].armorDescription

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