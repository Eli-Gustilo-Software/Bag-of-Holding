package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.ArmorListRecyclerAdapter
import com.example.thebagofholding.R

class ArmorListFragment : Fragment(){
    private lateinit var armorListRecyclerView: RecyclerView
    private var armorListRecyclerAdapter: ArmorListRecyclerAdapter? = null
    private var armorListTestArray = ArrayList<String>()

    init {
        armorListTestArray.add("Sword of Doom")
        armorListTestArray.add("Sword of t")
        armorListTestArray.add("Sword of f")
        armorListTestArray.add("Sword of g")
        armorListTestArray.add("Sword of b")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_armor_apparal_list, container, false)
        armorListRecyclerView = root.findViewById(R.id.armor_list_recyclerView)
        if (armorListRecyclerAdapter == null){ //create it
            armorListRecyclerAdapter = ArmorListRecyclerAdapter(armorListTestArray)
            armorListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
            armorListRecyclerView.adapter = armorListRecyclerAdapter
            armorListRecyclerAdapter!!.notifyDataSetChanged()
        }else{//is created, so update.

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}