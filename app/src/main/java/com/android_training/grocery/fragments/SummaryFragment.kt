package com.android_training.grocery.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android_training.class15.R
import com.android_training.grocery.app.Config
import com.android_training.grocery.helpers.DBHelper
import kotlinx.android.synthetic.main.fragment_summary.*
import kotlinx.android.synthetic.main.fragment_summary.view.*

class SummaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_summary, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        var dbHelper =
            DBHelper(view.context)
        var subtotal = dbHelper.getRetailPrice()
        var orderAmount = dbHelper.getOurPrice()+ Config.DELIVERY_FEE
        var discount = dbHelper.getDiscount()

        view.txt_subtotal.text = "$ " + String.format("%.2f", subtotal)
        view.txt_discount.text = "$ " + String.format("%.2f", discount)
        view.txt_delivery_charges.text = "$ " + String.format("%.2f", Config.DELIVERY_FEE)
        view.txt_order_amount.text = "$ " + String.format("%.2f", orderAmount)
    }

    companion object {
    }
}