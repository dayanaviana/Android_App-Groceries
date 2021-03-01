package com.android_training.grocery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android_training.class15.R
import com.android_training.grocery.helpers.SessionManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {

        var session = SessionManager(this)

        if (session.isLoggedIn()) {
            var intent = Intent(this@MainActivity, CategoriesActivity::class.java)
            startActivity(intent)
        }else{
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}