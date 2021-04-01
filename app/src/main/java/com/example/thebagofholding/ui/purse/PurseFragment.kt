package com.example.thebagofholding.ui.purse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.thebagofholding.R

class PurseFragment : Fragment() {
    private lateinit var purseViewModel: PurseViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_purse, container, false)



        purseViewModel = ViewModelProvider(this).get(PurseViewModel::class.java)
        purseViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bronze = view?.findViewById<TextView>(R.id.purse_money_1_4_textview)
        val silver = view?.findViewById<TextView>(R.id.purse_money_2_4_textview)
        val gold = view?.findViewById<TextView>(R.id.purse_money_3_4_textview)
        val truesilver = view?.findViewById<TextView>(R.id.purse_money_4_4_textview)
        val bronzeAmount = view?.findViewById<TextView>(R.id.purse_money_1_4_amount_edittext)
        val silverAmount = view?.findViewById<TextView>(R.id.purse_money_2_4_amount_edittext)
        val goldAmount = view?.findViewById<TextView>(R.id.purse_money_3_4_amount_edittext)
        val truesilverAmount = view?.findViewById<TextView>(R.id.purse_money_4_4_amount_edittext)

        bronze?.text = "Bronze"
        silver?.text = "Silver"
        gold?.text = "Gold"
        truesilver?.text = "True Silver"
        bronzeAmount?.text = "156"
        silverAmount?.text = "41865"
        goldAmount?.text = "4899"
        truesilverAmount?.text = "4564566"
    }
}