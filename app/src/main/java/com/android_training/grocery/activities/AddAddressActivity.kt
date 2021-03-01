package com.android_training.grocery.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.helpers.SessionManager
import com.android_training.grocery.models.AddressPostResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        txt_error_msg.visibility = View.GONE
        init()
    }

    private fun init() {
        setupToolbar()
    }

    fun radioButton_onCLick(v: View){
//        var view = this.currentFocus
        var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken,0)
    }

    private fun setupToolbar(){
        var toolbar: Toolbar = toolbar_view
        toolbar.title = "Add new Address"
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

    fun newAdress_onClick(v: View){
        var houseNo = edt_address_number.text.toString()
        var streetName = edt_address_street.text.toString()
        var city = edt_address_city.text.toString()
        var zipCode = edt_address_zipcode.text.toString()
        var type = getAddresType()
        var userId = SessionManager(v.context)
            .getUserId()

        var myJson = JSONObject()
        myJson.put("pincode", zipCode)
        myJson.put("userId", userId)
        myJson.put("houseNo", houseNo)
        myJson.put("streetName", streetName)
        myJson.put("city", city)
        myJson.put("type", type)

        postAddress(myJson)
    }

    private fun getAddresType(): String {
        var selected = radio_group.checkedRadioButtonId
        var radioButton: RadioButton = findViewById(selected)
        return radioButton.text.toString()
    }

    fun postAddress(myJson: JSONObject){
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.getAddress(),
            myJson,
            Response.Listener {
                var gson = Gson()
                var addressPostResponse = gson.fromJson(it.toString() , AddressPostResponse::class.java)
                var msg = addressPostResponse.message
                Log.d("myApp", "POST Address: $msg")
                if(!addressPostResponse.error) {
//                    var data: Address = addressPostResponse.data
                    //Return to Address Activity
                    finish()
                }else {
                    txt_error_msg.visibility = View.VISIBLE
                    txt_error_msg.text = msg
//                    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener {
                txt_error_msg.visibility = View.VISIBLE
                txt_error_msg.text = "An error occurred."
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("myApp", "POST Address: ${it.message}")
            }
        )
        requestQueue.add(request)
    }

}