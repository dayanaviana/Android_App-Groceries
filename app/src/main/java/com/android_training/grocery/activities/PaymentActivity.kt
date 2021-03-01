package com.android_training.grocery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.app.Config
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.helpers.DBHelper
import com.android_training.grocery.helpers.SessionManager
import com.android_training.class15.models.*
import com.android_training.grocery.models.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_confirmation.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_payment.txt_error_msg
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONArray
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {
    lateinit var session: SessionManager
    lateinit var dbHelper: DBHelper
    lateinit var selectedAddress: Address
    lateinit var orderData: OrderData
    lateinit var intentConfirmation: Intent
    private var userID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
    }
    private fun init() {
        setupToolbar()
        initializeVariables()
        getPOSTData()
        setupUI()
    }

    private fun setupToolbar(){
        var toolbar: Toolbar = toolbar_view
        toolbar.title = "Payment"
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

    private fun initializeVariables() {
        session = SessionManager(this)
        dbHelper = DBHelper(this)
        selectedAddress = intent.getSerializableExtra(Address.DATA) as Address
        intentConfirmation = Intent(this, ConfirmationActivity::class.java)
    }
    private fun setupUI(){
        txt_error_msg.visibility = View.GONE

        txt_user_name.text = session.getUser()
        txt_user_email.text = orderData.user.email
        txt_user_mobile.text = "Mobile: "+ orderData.user.mobile

        txt_address_type.text = "Type: " + orderData.shippingAddress.type
        txt_address_houseNo.text = orderData.shippingAddress.houseNo +", "
        txt_address_street.text = orderData.shippingAddress.streetName
        txt_address_city.text = orderData.shippingAddress.city +", "
        txt_adress_zipcode.text = "ZipCode: "+ orderData.shippingAddress.pincode.toString()
    }
    private fun getPOSTData() {
        var retailPrice = dbHelper.getRetailPrice()
        var ourPrice = dbHelper.getOurPrice()
        var orderSummary = OrderSummary(
            null,
            Config.DELIVERY_FEE,
            retailPrice - ourPrice,
            retailPrice,
            ourPrice,
            ourPrice + Config.DELIVERY_FEE
        )
        var orderPayment = OrderPayment(
            null,
            "cash",
            "completed"
        )
        var orderProducts = dbHelper.getAllProductOrder()
        var orderAddress = Address(
            city = selectedAddress.city!!,
            houseNo = selectedAddress.houseNo!!,
            pincode = selectedAddress.pincode!!,
            streetName = selectedAddress.streetName!!,
            type = selectedAddress.type!!
        )
        var orderUser =
            User(
                _id = session.getUserId()!!,
                email = session.getEmail()!!,
                mobile = session.getMobile() ?: ""
            )

        orderData = OrderData(
            null, null, null,
            orderSummary,
            orderPayment,
            orderProducts,
            orderAddress,
            orderUser,
            session.getUserId()!!
        )
        intentConfirmation.putExtra(OrderData.DATA, orderData)
    }

    fun btnPayCash_onClick(view: View){
        startActivity(intentConfirmation)
    }
}