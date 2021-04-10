package com.example.thebagofholding.ui.mainbag.miscellaneouslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thebagofholding.*
import com.example.thebagofholding.ui.mainbag.consumableslist.ConsumablesListRecyclerAdapter
import com.example.thebagofholding.ui.mainbag.consumableslist.ConsumablesListViewModel

class MiscellaneousListFragment : Fragment(){
    private lateinit var miscListRecyclerView: RecyclerView
    private lateinit var miscListViewModel : MiscellaneousListViewModel
    private lateinit var miscListOwnerTextView : TextView
    private var miscListRecyclerAdapter: MiscellaneousListRecyclerAdapter? = null
    private var miscListArray = ArrayList<MiscellaneousItemData>()
    private lateinit var currentCharacter : CharacterInformation

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!//TODO is this current character infromation?? Rename?
            for (item in currentCharacter.characterMiscellaneousItemList!!){
                miscListArray.add(item)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.bag_misc_list, container, false)

        //ViewModel and RecyclerViewAdapter
        miscListViewModel = ViewModelProvider(this).get(MiscellaneousListViewModel::class.java)
        miscListViewModel.characterData.observe(viewLifecycleOwner, Observer {
            miscListRecyclerView = root.findViewById(R.id.misc_list_recyclerView)
            if (miscListRecyclerAdapter == null){ //create it
                miscListRecyclerAdapter = MiscellaneousListRecyclerAdapter(currentCharacter)
                miscListRecyclerView.layoutManager = LinearLayoutManager(this.activity)
                miscListRecyclerView.adapter = miscListRecyclerAdapter
                miscListRecyclerAdapter!!.notifyDataSetChanged()
            }else{//is created, so update.
                miscListRecyclerAdapter!!.updateData(currentCharacter)
                miscListRecyclerAdapter!!.notifyDataSetChanged()
            }
        })

        //Character Name TextView
        miscListOwnerTextView = root.findViewById(R.id.misc_list_title)
        miscListOwnerTextView.text = "${currentCharacter.characterName}'s Miscellaneous Items"

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}