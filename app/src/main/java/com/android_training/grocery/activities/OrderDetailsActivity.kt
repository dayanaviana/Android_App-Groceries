package com.android_training.grocery.activities

import android.net.ParseException
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android_training.class15.R
import com.android_training.grocery.adapters.AdapterProduct
import com.android_training.grocery.models.OrderData
import kotlinx.android.synthetic.main.activity_order_details.*
import java.text.SimpleDateFormat

class OrderDetailsActivity : AppCompatActivity() {
    lateinit var adapterProduct: AdapterProduct
    lateinit var orderData: OrderData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        init()
    }

    private fun init() {
        orderData = intent.getSerializableExtra(OrderData.DATA) as OrderData
        loadUI()

        //Generate list of Products
        adapterProduct =
            AdapterProduct(this)
        adapterProduct.mList = orderData.products

        //TODO: Setup Recyclerview
        recycler_view.adapter = adapterProduct
        recycler_view.layoutManager = LinearLayoutManager(this)
    }

    private fun loadUI() {
        txt_order_date.text = orderData.date

//        txt_user_name.text = orderData.user.name
        txt_user_email.text = orderData.user.email
        txt_user_mobile.text = "Mobile: " + orderData.user.mobile

        txt_address_type.text = orderData.shippingAddress.type
        txt_address_houseNo.text = orderData.shippingAddress.houseNo
        txt_address_street.text = orderData.shippingAddress.streetName
        txt_address_city.text = orderData.shippingAddress.city + ", "
        txt_adress_zipcode.text = "ZipCode: " + orderData.shippingAddress.pincode

        txt_order_ourPrice.text = "Our Price: " +
                "$ " + String.format("%.2f", orderData.orderSummary.ourPrice)
        txt_order_amount.text = "Order Amount: " +
                "$ " + String.format("%.2f", orderData.orderSummary.orderAmount)
        txt_order_discount.text = "Discount: " +
                "$ " + String.format("%.2f", orderData.orderSummary.discount)
        txt_order_delivery.text = "Delivery Charges: " +
                "$ " + String.format("%.2f", orderData.orderSummary.deliveryCharges)
        txt_order_total.text = "Total Amount: " +
                "$ " + String.format("%.2f", orderData.orderSummary.totalAmount)
    }

    fun convertDate(date: String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("MMM d, yyyy")
        try {
            val finalStr: String = outputFormat.format(inputFormat.parse(date))
            println(finalStr)
            return finalStr
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }
}