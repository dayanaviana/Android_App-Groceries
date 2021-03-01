package com.android_training.grocery.helpers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.android_training.class15.R
import com.android_training.grocery.activities.CartActivity
import kotlinx.android.synthetic.main.menu_cart_layout.view.*

abstract class CartBadgeHelper: AppCompatActivity() {
    //Track count of items in cart
    private var textViewCartCount: TextView? = null
    protected lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DBHelper(this)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        //Find item in menu
        var item = menu.findItem(R.id.item_cart)
        //Add layout to menu item
        MenuItemCompat.setActionView(item, R.layout.menu_cart_layout)
        var view = MenuItemCompat.getActionView(item)
        // Make TextView change dinamically
        textViewCartCount = view.txt_cart_badge

        updateCartCount()

        view.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }
        return true
    }
    protected fun updateCartCount() {
        var count = dbHelper.getCartQuantityTotal()
        if(count==0){
            textViewCartCount?.visibility = View.GONE
        }else{
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }

    }
    //Setup Menu itens
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_cart -> startActivity(Intent(this, CartActivity::class.java))
        }
        return true
    }

    override fun onRestart() {
        super.onRestart()
        updateCartCount()
    }
}