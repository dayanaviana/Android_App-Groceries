package com.android_training.grocery.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.adapters.CategoryAdapter
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.helpers.CartBadgeHelper
import com.android_training.grocery.helpers.DBHelper
import com.android_training.grocery.helpers.SessionManager
import com.android_training.grocery.helpers.VolleyHelper
import com.android_training.grocery.models.Category
import com.android_training.grocery.models.CategoryResponse
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_categories_layout.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_drawer.*
import kotlinx.android.synthetic.main.nav_header.view.*

class CategoriesActivity: CartBadgeHelper(),
    //Support clickable drawer items
    NavigationView.OnNavigationItemSelectedListener
{
    //Drawer Layout properties
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    //Track count of items in cart
    private var textViewCartCount: TextView? = null

    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_categories_layout)
        setContentView(R.layout.layout_drawer)

        categoryAdapter =
            CategoryAdapter(this)

        init()
    }

    private fun init() {
        setupToolbar()
        setupDrawerLayout()

        var categoriesList = getCategories()
//        var categoriesList = VolleyHelper.getCategories(this)
//        categoryAdapter.setData(categoriesList)

        recycler_view.adapter = categoryAdapter
        recycler_view.layoutManager = GridLayoutManager(this,2)
        //Scroll list horizontally
        //recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    //Config Toolbar
    private fun setupToolbar(){
        var toolbar: Toolbar = toolbar_view
        toolbar.title = "Home"
//        toolbar.subtitle = "Description"
        setSupportActionBar(toolbar)
    }

    //Volley network call
    private fun getCategories():  ArrayList<Category> {
        var mList:  ArrayList<Category> = ArrayList()

        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getCategory(),
            Response.Listener {
                progress_bar.visibility = View.GONE
                var gson = Gson()
                var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                mList = categoryResponse.data
                categoryAdapter.setData(categoryResponse.data)
//                Log.d("myApp", "Category Count: "+categoryResponse.count)
            },
            Response.ErrorListener {
                progress_bar.visibility = View.GONE
                Toast.makeText(applicationContext,"Categories Error", Toast.LENGTH_LONG ).show()
                Log.d("myApp", it.localizedMessage)
            }
        )

        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)

        return mList
    }

    private fun setupDrawerLayout() {
        drawerLayout = drawer_layout
        navView = nav_view
        var drawerHeader = navView.getHeaderView(0)

        var session = SessionManager(this)
        drawerHeader.txt_header_name.text = session.getUser() ?: "Guest"
        drawerHeader.txt_header_email.text = session.getEmail() ?: ""

        //set listener for menu items
        navView.setNavigationItemSelectedListener(this)

        //Add hamburguer menu
        var drawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar_view,0,0)
        drawerToggle.setHomeAsUpIndicator(R.drawable.corned_shape)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        Log.d("myApp","CategoriesActivity: nav menu item clicked")
        when(item.itemId){
            R.id.item_account -> Toast.makeText(applicationContext, "My Account", Toast.LENGTH_SHORT).show()
            R.id.item_order_history -> startActivity(Intent(this, OrderHistoryActivity::class.java))
            R.id.item_logout -> {
                dialogLogout()
//                Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
            }
            R.id.item_about -> Toast.makeText(applicationContext, "About me", Toast.LENGTH_SHORT).show()
        }
        //Close drawer after seleting any item on menu
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun dialogLogout() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("You will lose all data saved on your profile. " +
                "\nAre you sure you want to logout?")
        builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                logout()
            }
        })
        builder.setNegativeButton("No", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }
        })
        builder.create().show()
    }
    private fun logout(){
        var session = SessionManager(this)
        session.clear()
        session.logout()
        dbHelper.deleteAll()

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    //Setup back button to close drawer
    override fun onBackPressed() {
        Log.d("myApp", "CategoriesActivity: back button pressed")
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onRestart() {
        super.onRestart()
        super.updateCartCount()
    }
}