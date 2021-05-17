package com.example.thebagofholding.ui.mainbag.weaponlist

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*
import com.example.thebagofholding.ui.items.ItemDetailsDialogPopup

class WeaponListRecyclerAdapter (var currentCharacter: CharacterInformation) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val tag = "WeaponListRecyclerAdapater"

    class WeaponsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "WeaponsViewHolder"
        lateinit var itemWeaponData: WeaponItemData
        lateinit var itemCharacterOwner : CharacterInformation
        private val context = super.itemView.context
        val weaponNameTextView: TextView = view.findViewById(R.id.weapon_cell_name_textview)
        val weaponImageImageView: ImageView = view.findViewById(R.id.weapon_cell_imageview)
        val weaponEffectOneTextView: TextView = view.findViewById(R.id.weapon_cell_effect1_textview)
        val weaponDescriptionTextView : TextView = view.findViewById(R.id.weapon_cell_description_textview)
        private val weaponCellConstraintLayout : ConstraintLayout = view.findViewById(R.id.weapon_cell_constraint_layout)

        init {
            //Setup
            weaponCellConstraintLayout.setOnLongClickListener {
                val popupMenu= PopupMenu(view.context,it)
                popupMenu.inflate(R.menu.item_popup_menu)
                popupMenu.setOnMenuItemClickListener {item->
                    when(item.itemId)
                    {
                        R.id.item_popup_menu_delete->{
                            Log.d(tag, "delete menu called.")
                            DataMaster.deleteItemWeapon(itemCharacterOwner, itemWeaponData)
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
                                        Log.d(tag, "player to transfer item = ${player.otherPlayerCharacterName} and item = ${weaponNameTextView.text}")
                                        val v = ContextCompat.getSystemService(context, Vibrator::class.java)
                                        if (Build.VERSION.SDK_INT >= 26) {
                                            v?.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                                        } else {
                                            @Suppress("DEPRECATION")
                                            v?.vibrate(200)
                                        }
                                        Toast.makeText(context, "Transferring ${itemWeaponData.name} to ${player.otherPlayerCharacterName}" , Toast.LENGTH_SHORT).show()
                                        DataMaster.transferItemWeapon(player, itemWeaponData)
                                        true
                                    }
                                }
                            }
                            popupMenuTransfer.show()
                        }

                        R.id.item_popup_menu_details -> {
                            val itemDetailsDialogPopup = ItemDetailsDialogPopup()
                            val itemData = GenericItemData(
                                itemWeaponData.image,
                                itemWeaponData.name,
                                itemWeaponData.weaponEffectOne,
                                "weapon",
                                itemWeaponData.weaponDescription,
                                itemWeaponData.weaponUUID)
                            itemDetailsDialogPopup.itemDetailsDialogPopup(itemData, context)
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
        return currentCharacter.characterWeaponItemsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weapon_recycler_cell, parent, false)
        return WeaponsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val weaponObjectViewHolder = holder as WeaponsViewHolder
        val weaponName = currentCharacter.characterWeaponItemsList[position].name
        val weaponImage = currentCharacter.characterWeaponItemsList[position].image
        val weaponEffectOne = currentCharacter.characterWeaponItemsList[position].weaponEffectOne

        weaponObjectViewHolder.weaponNameTextView.text = weaponName
        weaponObjectViewHolder.weaponImageImageView.setBackgroundResource(weaponImage)
        weaponObjectViewHolder.weaponEffectOneTextView.text = weaponEffectOne
        weaponObjectViewHolder.weaponDescriptionTextView.text = currentCharacter.characterWeaponItemsList[position].weaponDescription

        holder.itemWeaponData = currentCharacter.characterWeaponItemsList[position]
        holder.itemCharacterOwner = currentCharacter
    }

    fun updateData(characterInformation: CharacterInformation){
        Log.d(tag, "updateData characterInformation = $characterInformation")
        currentCharacter = characterInformation
        notifyDataSetChanged()
    }
}