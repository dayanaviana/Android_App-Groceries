package com.android_training.grocery.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.app.Endpoints
import com.android_training.class15.models.*
import com.android_training.grocery.models.Address
import com.android_training.grocery.models.AddressPostResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.row_address_adapter.view.*

class AdapterAddress(var mContext: Context): RecyclerView.Adapter<AdapterAddress.MyViewHolder>() {

    var mList: ArrayList<Address> = ArrayList()
    lateinit var selectedAddress: Address
    var lastChecked: RadioButton? = null

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(address: Address, position: Int){
            itemView.txt_row_houseNo.text = address.houseNo
            itemView.txt_row_street.text = address.streetName
            itemView.txt_row_city.text = address.city
            itemView.txt_row_zipcode.text = address.pincode.toString()
            itemView.txt_row_type.text = address.type

            itemView.setOnClickListener{
                defineItemChecked(itemView,address)
            }
            itemView.radio_address.setOnClickListener{
                defineItemChecked(itemView,address)
            }
            itemView.btn_delete_item.setOnClickListener {
                deleteAddresses(address._id!!)
            }
        }
    }
    fun defineItemChecked(itemView: View, address: Address){
        //Change layout
        if(lastChecked != null) {
            //Uncheck last radio button
            lastChecked!!.isChecked = false
        }
        //Save last radio button checked
        lastChecked = itemView.radio_address
        //Check new radio button
        itemView.radio_address.isChecked = true
        //save address selected
        selectedAddress = address
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_address_adapter,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }

    fun setData(data: ArrayList<Address>){
        mList = data
        notifyDataSetChanged()
    }

    fun deleteAddresses(id: String) {
        var url = Endpoints.getAddressById(id)
        var request = StringRequest(
            Request.Method.DELETE,
            url,
            Response.Listener {
                Log.d("myApp", "Volley Listener")
                var gson = Gson()
                var response = gson.fromJson(it, AddressPostResponse::class.java)
                if (!response.error){
                    var address = response.data
                    mList.remove(address)
                    notifyDataSetChanged()
                }
//                Toast.makeText(mContext,response.message, Toast.LENGTH_SHORT ).show()
            },
            Response.ErrorListener {
                Log.d("myApp", "AdapterAdress: Volley ErrorListener")
                var statusCode = it.networkResponse.statusCode
                var message = it.message
                Toast.makeText(mContext,"DELETE ADDRESS ERROR", Toast.LENGTH_SHORT ).show()
            }
        )
        var requestQueue = Volley.newRequestQueue(mContext)
        requestQueue.add(request)
    }
}