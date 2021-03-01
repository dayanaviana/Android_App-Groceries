package com.android_training.grocery.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.android_training.class15.R
import com.android_training.grocery.adapters.AdapterCart
import com.android_training.grocery.fragments.SummaryFragment
import com.android_training.grocery.helpers.DBHelper
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_bar.*

class CartActivity : AppCompatActivity() {

    lateinit var adapterCart: AdapterCart
    lateinit var dbHelper: DBHelper
    var fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        dbHelper = DBHelper(this)
        adapterCart = AdapterCart(this)

        init()
    }

    private fun init() {
        setupToolbar()
        fragmentManager.beginTransaction()
            .add(R.id.fragment_summary,
                SummaryFragment()
            ).commit()

        adapterCart.setData(dbHelper.getAllCartItems())
        recycler_view.adapter = adapterCart
        recycler_view.layoutManager = LinearLayoutManager(this)

        btn_chekout.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }

    }
    fun updateFragment(){
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_summary,
                SummaryFragment()
            ).commit()
    }

    //Config Toolbar
    private fun setupToolbar(){
        var toolbar: Toolbar = toolbar_view
        toolbar.title = "My Cart"
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
