package com.example.thebagofholding.ui.mainbag.consumableslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*
import com.example.thebagofholding.ui.mainbag.armorlist.ArmorListRecyclerAdapter
import com.example.thebagofholding.ui.mainbag.armorlist.ArmorListViewModel

class ConsumablesListFragment : Fragment(){
    private lateinit var consumablesListRecyclerView: RecyclerView
    private lateinit var consumablesListViewModel: ConsumablesListViewModel
    private var consumablesListRecyclerAdapter: ConsumablesListRecyclerAdapter? = null
    private var consumablesListArray = ArrayList<ConsumablesItemData>()
    private lateinit var currentCharacter : CharacterInformation

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!//TODO is this current character infromation?? Rename?
            for (item in currentCharacter.characterConsumablesItemsList!!){
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

        //ViewModel and RecyclerViewAdapter
        consumablesListViewModel = ViewModelProvider(this).get(ConsumablesListViewModel::class.java)
        consumablesListViewModel.characterData.observe(viewLifecycleOwner, Observer {
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

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}