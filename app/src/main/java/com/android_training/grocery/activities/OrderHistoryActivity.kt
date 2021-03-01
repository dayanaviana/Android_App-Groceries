package com.android_training.grocery.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.adapters.AdapterOrders
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.helpers.SessionManager
import com.android_training.grocery.models.OrderHistoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.app_bar.*

class OrderHistoryActivity : AppCompatActivity() {
    lateinit var session: SessionManager
    lateinit var adapterOrders: AdapterOrders

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        session = SessionManager(this)
        adapterOrders = AdapterOrders(this)
        init()
    }

    private fun init() {
        setupToolbar()
        getOrdersHistory()

        recycler_history_view.adapter = adapterOrders
        recycler_history_view.layoutManager = LinearLayoutManager(this)
        recycler_history_view.addItemDecoration(
            DividerItemDecoration(recycler_history_view.getContext(), DividerItemDecoration.VERTICAL)
        )
    }

    private fun setupToolbar() {
        var toolbar: Toolbar = toolbar_view
        toolbar.title = "Purchase History"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    //Volley network call
    private fun getOrdersHistory(){
        var userId = session.getUserId() ?: ""

        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getOrdersByUserId(userId),
            Response.Listener {
                progress_bar.visibility = View.GONE
                var gson = Gson()
                var response = gson.fromJson(it, OrderHistoryResponse::class.java)
                adapterOrders.setData(response.data!!)
                Log.d("myApp", "Response Count: "+ response.count)
            },
            Response.ErrorListener {
                progress_bar.visibility = View.GONE
                Toast.makeText(applicationContext,"Error", Toast.LENGTH_LONG ).show()
                Log.d("myApp", it.localizedMessage)
            }
        )
        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)

    }
}