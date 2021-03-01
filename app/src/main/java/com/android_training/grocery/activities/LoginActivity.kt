package com.android_training.grocery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.helpers.SessionManager
import com.android_training.grocery.models.User
import com.android_training.grocery.models.UserResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
    }

    private fun init() {
        txt_new_user.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {

            var username = edt_login_email.text
            var password = edt_login_password.text

            var myJson = JSONObject()
            myJson.put("email", username)
            myJson.put("password", password)
            //JSONObjext(Gson().toJson(...))

            getLoginRequest(myJson)
            var session =
                SessionManager(this)
            if(session.isLoggedIn()){
                var intent = Intent(this, CategoriesActivity::class.java)
                startActivity(intent)
            }

        }
    }

    fun getLoginRequest(param:JSONObject){
        var requestQueue = Volley.newRequestQueue(this)

        var request = JsonObjectRequest(
            Request.Method.POST,
            Endpoints.getLogin(),
            param,
            Response.Listener {
                var gson = Gson()
                var userResponse = gson.fromJson(it.toString() , UserResponse::class.java)
                var u: User = userResponse.user

                var session =
                    SessionManager(this)
                session.login(u._id!!, u.firstName!!, u.mobile!!, u.email!!, u.password!!)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
                Log.d("myApp", "Login status code: "+it.networkResponse.statusCode)
                Log.d("myApp", "Login message: "+it.message)
            }
        )
        requestQueue.add(request)
    }
}