package com.example.thebagofholding.ui.mainbag.weaponlist

import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*

class WeaponListRecyclerAdapter (var currentCharacter: CharacterInformation) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class WeaponsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tag = "WeaponsViewHolder"
        lateinit var itemWeaponData: WeaponItemData
        lateinit var itemCharacterOwner : CharacterInformation
        val weaponNameTextView: TextView = view.findViewById(R.id.weapon_cell_name_textview)
        val weaponImageImageView: ImageView = view.findViewById(R.id.weapon_cell_imageview)
        val weaponEffectOneTextView: TextView = view.findViewById(R.id.weapon_cell_effect1_textview)
        private val weaponCellConstraintLayout : ConstraintLayout = view.findViewById(R.id.weapon_cell_constraint_layout)

        init {
            //Setup
            weaponCellConstraintLayout.setOnLongClickListener(){
                val popupMenu= PopupMenu(view.context,it) //TODO need to move this to the right of the screen.
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
                            val popupMenuTransfer= PopupMenu(view.context, it) //TODO need to move this to the right of the screen.
                            //get list of all other characters found
                            if (DataMaster.findOtherPlayers().size == 0){
                                //no other players found
                                popupMenuTransfer.menu.add("No Nearby Players Found")
                            }else{
                                for (player in DataMaster.findOtherPlayers()){
                                    popupMenuTransfer.menu.add(player.otherPlayerCharacterName).setOnMenuItemClickListener {
                                        //player name clicked
                                        Log.d(tag, "player to transfer item = ${player.otherPlayerCharacterName} and item = ${weaponNameTextView.text}")
                                        DataMaster.transferItemWeapon(itemCharacterOwner, player, itemWeaponData)
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

        holder.itemWeaponData = currentCharacter.characterWeaponItemsList[position]
        holder.itemCharacterOwner = currentCharacter
    }

    fun updateData(characterInformation: CharacterInformation){
        currentCharacter = characterInformation
        notifyDataSetChanged()
    }
}