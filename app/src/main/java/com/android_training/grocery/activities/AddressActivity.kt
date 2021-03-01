package com.android_training.grocery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.adapters.AdapterAddress
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.helpers.SessionManager
import com.android_training.class15.models.*
import com.android_training.grocery.models.Address
import com.android_training.grocery.models.AddressResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.app_bar.*

class AddressActivity : AppCompatActivity() {
    lateinit var adapterAddress: AdapterAddress
    var mList: ArrayList<Address> = ArrayList()
    var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        adapterAddress =
            AdapterAddress(this)

        init()
    }

    private fun init() {
        setupToolbar()
        var session = SessionManager(this)
        userId = session.getUserId() ?: ""

        getAddresses(userId)

        recycler_view.adapter = adapterAddress
        recycler_view.layoutManager = LinearLayoutManager(this)

        btn_new_adress.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
        btn_select_adress.setOnClickListener {
            var selectedAddress: Address? = adapterAddress.selectedAddress
            if(selectedAddress!=null){
                var intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra(Address.DATA, selectedAddress)
                startActivity(intent)
            }
            else{
                Log.d("myAp","AddressActivity: Must select one address")
                Toast.makeText(this,"Must select one address", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupToolbar(){
        var toolbar: Toolbar = toolbar_view
        toolbar.title = "Address List"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return true
    }

    //Volley GET Call
    fun getAddresses(userId: String) {
        var url = Endpoints.getAddressById(userId)
        var request = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener {
                Log.d("myApp", "Volley Listener")
                var gson = Gson()
                var addressResponse = gson.fromJson(it, AddressResponse::class.java)
                adapterAddress.setData(addressResponse.data)
                progress_bar.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d("myApp", "Volley ErrorListener")
                var statusCode = it.networkResponse.statusCode
                var message = it.message
                Toast.makeText(this,"GET ADDRESS ERROR", Toast.LENGTH_LONG ).show()
                progress_bar.visibility = View.GONE
            }
        )
        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    override fun onRestart() {
        super.onRestart()
        progress_bar.visibility = View.VISIBLE
        getAddresses(userId)
    }
}