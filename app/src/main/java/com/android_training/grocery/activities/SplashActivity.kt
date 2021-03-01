package com.android_training.grocery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.android_training.class15.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        var handler = Handler()
        handler.postDelayed({
            loadActivity()
        }, 2000)
    }

    private fun loadActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }
}