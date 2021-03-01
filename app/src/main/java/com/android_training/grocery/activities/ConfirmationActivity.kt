package com.android_training.grocery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.helpers.DBHelper
import com.android_training.grocery.models.OrderData
import com.android_training.grocery.models.OrderResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_confirmation.*
import kotlinx.android.synthetic.main.activity_payment.*
import org.json.JSONObject

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)
        init()
    }

    private fun init() {
        var orderData = intent.getSerializableExtra(OrderData.DATA) as OrderData

        //POST Order
        var myJson = builOrderdJSON(orderData)
        postOrder(myJson)

        txt_order_details.setOnClickListener {
            var intent = Intent(this, OrderDetailsActivity::class.java)
            intent.putExtra(OrderData.DATA, orderData)
            startActivity(intent)
        }
    }

    private fun builOrderdJSON(orderData: OrderData): JSONObject {
        var gson = Gson()
        var order = gson.toJson(orderData)
        return JSONObject(order)
    }
    fun postOrder(myJson: JSONObject){
        var requestQueue = Volley.newRequestQueue(this)
        var url = Endpoints.getOrders()
        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            myJson,
            Response.Listener {
                var gson = Gson()
                var orderResponse = gson.fromJson(it.toString() , OrderResponse::class.java)
                if(!orderResponse.error) {
                    txt_order_details.visibility = View.VISIBLE
                    //Empty car after order is placed
                    DBHelper(this).deleteAll()
                }else {
                    img_thank_you.visibility = View.GONE
                    txt_sorry.text = "Sorry \n Error Occured"
                }
                txt_order_response.text = orderResponse.message
            },
            Response.ErrorListener {
                img_thank_you.visibility = View.GONE
                txt_sorry.visibility = View.VISIBLE
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("myApp", "ConfirmationActivity: Volley Error ${it.message}")
            }
        )
        requestQueue.add(request)
    }

    override fun onBackPressed() {
        returnToHome()
    }
    fun btnReturnToHome_onClick(v: View){
        returnToHome()
    }
    fun returnToHome(){
        startActivity(Intent(this, CategoriesActivity::class.java))
    }
}