package com.android_training.grocery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android_training.class15.R
import com.android_training.grocery.app.Config
import com.android_training.grocery.fragments.SummaryFragment
import com.android_training.grocery.helpers.DBHelper
import com.android_training.grocery.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_cart_adapter.view.*


class AdapterCart(var mContext: Context):RecyclerView.Adapter<AdapterCart.MyViewHolder>() {
    var mList: ArrayList<Product> = ArrayList()
    var dbHelper = DBHelper(mContext)

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(p: Product, position: Int){
            itemView.txt_prod_name.text = p.productName
            var price = p.price
            itemView.txt_prod_price.text = "$ " + String.format("%.2f", price)
            var mrp = p.mrp
            itemView.txt_prod_mrp.text = "$ " + String.format("%.2f", mrp)
            var save = price?.let { mrp?.minus(it) }
            itemView.txt_prod_save.text = "$ " + String.format("%.2f", save)
            updateUI(itemView,p)

            Picasso
                .get()
                .load(Config.IMAGE_URL + p.image)
                .into(itemView.img_product)

            itemView.btn_delete_item.setOnClickListener {
                dbHelper.deleteCartItem(p)
                mList.remove(p)
                notifyDataSetChanged()
                updateUI(itemView,p)
//                Toast.makeText(mContext,"Deleted",Toast.LENGTH_LONG).show()
            }
            itemView.btn_add_qnt.setOnClickListener {
                p.quantity = p.quantity?.plus(1)
                dbHelper.updateCartItem(p)
                updateUI(itemView,p)
//                Toast.makeText(mContext,"Added",Toast.LENGTH_LONG).show()
            }
            itemView.btn_subtract_qnt.setOnClickListener {
                p.quantity = p.quantity?.minus(1)
                dbHelper.updateCartItem(p)
                updateUI(itemView,p)
//                Toast.makeText(mContext,"Removed",Toast.LENGTH_LONG).show()
            }

        }
    }
    private fun updateUI(itemView: View, p: Product){
        itemView.txt_show_qnt.text = p.quantity.toString()

        //Hide minus button if there's only one item
        if(p.quantity==1) {
            itemView.btn_subtract_qnt.visibility = View.INVISIBLE
        }else if(p.quantity==2){
            itemView.btn_subtract_qnt.visibility = View.VISIBLE
        }

        //Update Fragment
        var activity = itemView.context as AppCompatActivity
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_summary,
                SummaryFragment()
            ).commit()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_cart_adapter,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }
    fun setData(data: ArrayList<Product>){
        mList = data
        notifyDataSetChanged()
    }
}