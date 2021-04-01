package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.ConsumablesListRecyclerAdapter
import com.example.thebagofholding.R

class ConsumablesListFragment : Fragment(){
    private lateinit var consumablesListRecyclerView: RecyclerView
    private var consumablesListRecyclerAdapter: ConsumablesListRecyclerAdapter? = null
    private var consumablesListTestArray = ArrayList<String>()

    init {
        consumablesListTestArray.add("Sword of Doom")
        consumablesListTestArray.add("Sword of t")
        consumablesListTestArray.add("Sword of f")
        consumablesListTestArray.add("Sword of g")
        consumablesListTestArray.add("Sword of b")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_consumables_list, container, false)
        consumablesListRecyclerView = root.findViewById(R.id.consumables_list_recyclerView)
        if (consumablesListRecyclerAdapter == null){ //create it
            consumablesListRecyclerAdapter = ConsumablesListRecyclerAdapter(consumablesListTestArray)
            consumablesListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
            consumablesListRecyclerView.adapter = consumablesListRecyclerAdapter
            consumablesListRecyclerAdapter!!.notifyDataSetChanged()
        }else{//is created, so update.

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}