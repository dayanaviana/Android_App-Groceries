package com.android_training.grocery.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android_training.class15.R
import com.android_training.grocery.activities.SubCategoryActivity
import com.android_training.grocery.app.Config
import com.android_training.grocery.models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_adapter.view.*

class CategoryAdapter(var mContext: Context) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    var mList: ArrayList<Category> = ArrayList()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cat: Category, position: Int) {
            itemView.txt_cat_slug.text = cat.slug

            Picasso
                .get()
                .load(Config.IMAGE_URL + cat.catImage)
                .into(itemView.img_category)

            itemView.setOnClickListener {
                var intent = Intent(mContext, SubCategoryActivity::class.java)
                intent.putExtra(Category.DATA, cat)
                mContext.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_category_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var category = mList[position]
        holder.bind(category, position)
    }

    fun setData(data: ArrayList<Category>) {
        mList = data
        notifyDataSetChanged()
    }
}