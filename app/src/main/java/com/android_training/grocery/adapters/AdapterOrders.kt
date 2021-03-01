package com.android_training.grocery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android_training.class15.R
import com.android_training.class15.models.*
import com.android_training.grocery.models.OrderData
import kotlinx.android.synthetic.main.row_order_history.view.*

class AdapterOrders(var mContext: Context) : RecyclerView.Adapter<AdapterOrders.MyViewHolder>(){
    var mList: ArrayList<OrderData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_history, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }
    fun setData(data: ArrayList<OrderData>){
        mList = data
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(o: OrderData, position: Int){
            itemView.txt_order_id.text = o._id
            itemView.txt_order_date.text = o.date
            itemView.txt_pay_mode.text = o.payment.paymentMode
            itemView.txt_pay_status.text = o.payment.paymentStatus

            itemView.txt_address_type.text = o.shippingAddress.type
            itemView.txt_address_houseNo.text = o.shippingAddress.houseNo
            itemView.txt_address_street.text = o.shippingAddress.streetName
            itemView.txt_address_city.text = o.shippingAddress.city
            itemView.txt_adress_zipcode.text = o.shippingAddress.pincode.toString()

            itemView.txt_order_ourPrice.text = "Our Price: " + o.orderSummary.ourPrice
            itemView.txt_order_amount.text = "Order Amount: " + o.orderSummary.orderAmount
            itemView.txt_order_discount.text = "Discount: " + o.orderSummary.discount
            itemView.txt_order_delivery.text = "Delivery Fees: " + o.orderSummary.deliveryCharges
            itemView.txt_order_total.text = "Order Toal: " + o.orderSummary.totalAmount
        }
    }
}