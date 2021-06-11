package com.eligustilosoftware.thebagofholding.ui.mainbag.miscellaneouslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eligustilosoftware.thebagofholding.CharacterInformation
import com.eligustilosoftware.thebagofholding.DataMaster
import com.eligustilosoftware.thebagofholding.MiscellaneousItemData
import com.eligustilosoftware.thebagofholding.R
import com.eligustilosoftware.thebagofholding.ui.items.NewInListItemPopup

class MiscellaneousListFragment : Fragment(){
    private lateinit var miscListRecyclerView: RecyclerView
    private lateinit var newItemButton : ImageView
    private lateinit var miscListViewModel : MiscellaneousListViewModel
    private var miscListRecyclerAdapter: MiscellaneousListRecyclerAdapter? = null
    private var miscListArray = ArrayList<MiscellaneousItemData>()
    private lateinit var currentCharacter : CharacterInformation

    init {
        if (DataMaster.retrieveCharacterInformation() != null){
            currentCharacter = DataMaster.retrieveCharacterInformation()!!
            for (item in currentCharacter.characterMiscellaneousItemList){
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

        //toolbar
        (activity as AppCompatActivity?)!!.supportActionBar?.customView?.findViewById<TextView>(R.id.toolbar_title_textview)?.text = getString(
                    R.string.toolbar_title_misc)

        //ViewModel and RecyclerViewAdapter
        miscListViewModel = ViewModelProvider(this).get(MiscellaneousListViewModel::class.java)
        miscListViewModel.characterData.observe(viewLifecycleOwner, {
            currentCharacter = it
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

        //New Item Button
        newItemButton = root.findViewById(R.id.new_item_buttonm)
        newItemButton.setOnClickListener {
            val newInListItemPopup = NewInListItemPopup(this.requireContext(), "misc")
            newInListItemPopup.newItemDetailsDialogPopup()
        }
        return root
    }
}