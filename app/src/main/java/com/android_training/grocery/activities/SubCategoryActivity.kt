package com.android_training.grocery.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.adapters.MyFragmentAdapter
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.helpers.CartBadgeHelper
import com.android_training.grocery.helpers.DBHelper
import com.android_training.grocery.models.Category
import com.android_training.grocery.models.SubcategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*
import kotlinx.android.synthetic.main.tableview_layout.*

class SubCategoryActivity : CartBadgeHelper(){

    lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tableview_layout)

        dbHelper = DBHelper(this)

        init()
    }

    private fun init() {
        //Receive Category to filter
        category = intent.getSerializableExtra(Category.DATA) as Category
//        Log.d("myApp", "SubcategoryActivity: CatID=${category.catId}")


        setupToolbar()

        //Network Request - Volley
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getSubCategoryById(category.catId),
            Response.Listener {
                var gson = Gson()
                var subcategoryResponse = gson.fromJson(it, SubcategoryResponse::class.java)
                var fragmentAdapter =
                    MyFragmentAdapter(
                        supportFragmentManager
                    )
                for (i in 0 until subcategoryResponse.data.size) {
                    fragmentAdapter.addFragment(subcategoryResponse.data[i])
                }
                //SETUP TAB LAYOUT & VIEW ADAPTER With Subcategories
                view_pager.adapter = fragmentAdapter
                tab_layout.setupWithViewPager(view_pager)
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                Log.d("myApp", it.localizedMessage)
            }
        )
        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    private fun setupToolbar(){
        var toolbar: Toolbar = toolbar_view
        toolbar.title = category.catName
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
}