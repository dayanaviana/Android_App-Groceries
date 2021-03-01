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
import com.android_training.class15.R
import com.android_training.grocery.app.Config
import com.android_training.grocery.helpers.CartBadgeHelper
import com.android_training.grocery.helpers.DBHelper
import com.android_training.grocery.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*
import kotlinx.android.synthetic.main.row_cart_adapter.view.*

class ProductDetailsActivity: CartBadgeHelper() {

    var product: Product? = null
//    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        product = intent.getSerializableExtra(Product.DATA)  as Product
        dbHelper = DBHelper(this)

        init()
    }

    private fun init() {
        setupToolbar()
        updateUI()

        txt_prod_detail_name.text = product?.productName
        txt_prod_detail_desc.text = product?.description
        txt_prod_detail_price.text = "$ " + String.format("%.2f", product?.price)

        Picasso
            .get()
            .load(Config.IMAGE_URL + product?.image)
            .into(img_prod_detail)

        btn_prod_detail_addCart.setOnClickListener {
            var inserted_cart_item = dbHelper.addCartItem(product!!)
            if (inserted_cart_item>0){
//                Toast.makeText(applicationContext, "Item added", Toast.LENGTH_LONG).show()
            }else {
                Toast.makeText(applicationContext, "ERROR adding item", Toast.LENGTH_LONG).show()
            }
            updateUI()
        }

        btn_add_qnt.setOnClickListener {
            var qnt = dbHelper.getItemQuantity(product!!)
            product?.quantity = qnt + 1
            dbHelper.updateCartItem(product!!)
            updateUI()
        }

        btn_subtract_qnt.setOnClickListener {
            var qnt = dbHelper.getItemQuantity(product!!)
            product?.quantity = qnt - 1
            if(product?.quantity==0){
                //Delete item
                dbHelper.deleteCartItem((product!!))
            }else {
                dbHelper.updateCartItem(product!!)
            }
            updateUI()
        }

        btn_view_cart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun updateUI() {
        var qnt = dbHelper.getItemQuantity(product!!)
        //Update quantity in Cart
        txt_show_qnt.text = qnt.toString()
        when(qnt) {
            0 -> {
                //Show ADD to Cart
                btn_prod_detail_addCart.visibility = View.VISIBLE
                layout_add_remove.visibility = View.GONE
            }

            1 -> {
                //Show ADD/REMOVE Itens
                btn_prod_detail_addCart.visibility = View.GONE
                layout_add_remove.visibility = View.VISIBLE
            }
        }
        super.updateCartCount()
    }

    private fun setupToolbar(){
        var toolbar: Toolbar = toolbar_view
        toolbar.title = "Product Detail"
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

    override fun onRestart() {
        super.onRestart()
        updateUI()
    }
}