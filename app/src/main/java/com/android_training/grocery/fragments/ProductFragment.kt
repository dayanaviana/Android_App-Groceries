package com.android_training.grocery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android_training.class15.R
import com.android_training.grocery.adapters.AdapterProduct
import com.android_training.grocery.app.Endpoints
import com.android_training.grocery.models.Product
import com.android_training.grocery.models.ProductResponse
import com.android_training.grocery.models.Subcategory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.recycler_layout.view.*

class ProductFragment : Fragment() {
    private var mProducts: ArrayList<Product> = ArrayList()
    private var subcatId: Int = 0

    lateinit var adapterProduct: AdapterProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subcatId = it.getInt(Subcategory.DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.recycler_layout, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {

        //Generate list of Products
        adapterProduct =
            AdapterProduct(activity!!)
        getData(subcatId, view)
        mProducts = adapterProduct.mList

        //TODO: Setup Recyclerview
        view.recycler_view.adapter = adapterProduct
        view.recycler_view.layoutManager = LinearLayoutManager(activity!!)
    }

    companion object {
        @JvmStatic
        fun newInstance(subcatId: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(Subcategory.DATA, subcatId)
                }
            }
    }

    private fun getData(subcatId: Int, view: View){
        //Get Products from API
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductBySubId(subcatId),
            Response.Listener {
                view.progress_bar.visibility = View.GONE
                var gson = Gson()
                var productResponse = gson.fromJson(it, ProductResponse::class.java)
                adapterProduct.setData(productResponse.data)
            },
            Response.ErrorListener {
                view.progress_bar.visibility = View.GONE
                Log.d("myApp", it.localizedMessage)
            }
        )

        var requestQueue = Volley.newRequestQueue(activity!!)
        requestQueue.add(request)
    }
}