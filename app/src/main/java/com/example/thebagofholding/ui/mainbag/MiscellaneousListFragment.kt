package com.example.thebagofholding.ui.mainbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.MiscellaneousListRecyclerAdapter
import com.example.thebagofholding.R

class MiscellaneousListFragment : Fragment(){
    private lateinit var miscListRecyclerView: RecyclerView
    private var miscListRecyclerAdapter: MiscellaneousListRecyclerAdapter? = null
    private var miscListTestArray = ArrayList<String>()

    init {
        miscListTestArray.add("Sword of Doom")
        miscListTestArray.add("Sword of t")
        miscListTestArray.add("Sword of f")
        miscListTestArray.add("Sword of g")
        miscListTestArray.add("Sword of b")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_misc_list, container, false)
        miscListRecyclerView = root.findViewById(R.id.misc_list_recyclerView)
        if (miscListRecyclerAdapter == null){ //create it
            miscListRecyclerAdapter = MiscellaneousListRecyclerAdapter(miscListTestArray)
            miscListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
            miscListRecyclerView.adapter = miscListRecyclerAdapter
            miscListRecyclerAdapter!!.notifyDataSetChanged()
        }else{//is created, so update.

        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}