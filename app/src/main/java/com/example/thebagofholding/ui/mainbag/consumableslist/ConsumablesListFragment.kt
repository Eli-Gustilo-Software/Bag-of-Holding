package com.example.thebagofholding.ui.mainbag.consumableslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.CharacterInformation
import com.example.thebagofholding.ConsumablesItemData
import com.example.thebagofholding.DataMaster
import com.example.thebagofholding.R
import com.example.thebagofholding.ui.items.NewArmorItemPopup
import com.example.thebagofholding.ui.items.NewConsumablesItemPopup

class ConsumablesListFragment : Fragment(){
    private lateinit var consumablesListRecyclerView: RecyclerView
    private lateinit var newItemButton : ImageView
    private lateinit var consumablesListViewModel: ConsumablesListViewModel
    private var consumablesListRecyclerAdapter: ConsumablesListRecyclerAdapter? = null
    private var consumablesListArray = ArrayList<ConsumablesItemData>()
    private lateinit var currentCharacter : CharacterInformation

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!
            for (item in currentCharacter.characterConsumablesItemsList){
                consumablesListArray.add(item)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_consumables_list, container, false)

        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = getString(
                    R.string.toolbar_title_consumables)

        //ViewModel and RecyclerViewAdapter
        consumablesListViewModel = ViewModelProvider(this).get(ConsumablesListViewModel::class.java)
        consumablesListViewModel.characterData.observe(viewLifecycleOwner, {
            currentCharacter = it
            consumablesListRecyclerView = root.findViewById(R.id.consumables_list_recyclerView)
            if (consumablesListRecyclerAdapter == null){ //create it
                consumablesListRecyclerAdapter = ConsumablesListRecyclerAdapter(currentCharacter)
                consumablesListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                consumablesListRecyclerView.adapter = consumablesListRecyclerAdapter
                consumablesListRecyclerAdapter!!.notifyDataSetChanged()
            }else{//is created, so update.
                consumablesListRecyclerAdapter!!.updateData(currentCharacter)
                consumablesListRecyclerAdapter!!.notifyDataSetChanged()
            }
        })

        //New Item Button
        newItemButton = root.findViewById(R.id.new_item_buttonc)
        newItemButton.setOnClickListener {
            val newItemPopup = NewConsumablesItemPopup()
            newItemPopup.consumableitemDetailsDialogPopup(this.requireContext())
        }

        return root
    }
}