package com.android_training.grocery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.Response
import com.android_training.class15.R
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.helpers.SessionManager
import com.android_training.grocery.models.Register
import com.android_training.grocery.models.RegisterResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()
    }

    private fun init() {
        btn_register.setOnClickListener {

            var name = edt_reg_name.text.toString()
            var email = edt_reg_email.text.toString()
            var mobile = edt_reg_mobile.text.toString()
            var password = edt_reg_password.text.toString()

            var myJson = JSONObject()
            myJson.put("firstName", name)
            myJson.put("email", email)
            myJson.put("password", password)
            myJson.put("mobile", mobile)
            //JSONObjext(Gson().toJson(...))

            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getRegister(),
                myJson,
                Response.Listener {

                    var gson = Gson()
                    var registerResponse = gson.fromJson(it.toString() , RegisterResponse::class.java)
                    var data: Register = registerResponse.data
                    var session =
                        SessionManager(this)
                    session.register(data.firstName, data.mobile, data.email, data.password)

                    Toast.makeText(
                        applicationContext,
                        "User Registered Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                },
                Response.ErrorListener {
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            )
            requestQueue.add(request)
        }
    }
}