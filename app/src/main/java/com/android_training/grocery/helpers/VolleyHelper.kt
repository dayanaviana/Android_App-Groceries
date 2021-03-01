package com.android_training.grocery.helpers

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android_training.grocery.app.Endpoints
import com.android_training.class15.models.*
import com.android_training.grocery.models.Category
import com.android_training.grocery.models.CategoryResponse
import com.google.gson.Gson
import org.json.JSONObject

class VolleyHelper {
    companion object {
        /*
        fun postAddress(mContext: Context, myJson: JSONObject, userId: String) {
            var url = Endpoints.getAddress(userId)
            var requestQueue = Volley.newRequestQueue(mContext)
            var request = JsonObjectRequest(
                Request.Method.POST,
                Endpoints.getAddress(userId),
                myJson,
                Response.Listener {
                    var gson = Gson()
                    var addressResponse = gson.fromJson(it.toString(), AddressResponse::class.java)
                    //var data: Address = addressResponse.data[0]

                    Toast.makeText(mContext, "Address Registered Successfully", Toast.LENGTH_SHORT)
                        .show()
                },
                Response.ErrorListener {
                    var statusCode = it.networkResponse.statusCode
                    var message = it.message
                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show()
                }
            )
            requestQueue.add(request)
        }
        */
        fun getCategories(mContext: Context):  ArrayList<Category>? {
            var mList:  ArrayList<Category>? = null
            var url = Endpoints.getCategory()
            var request = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener {
                    Log.d("myApp", "getCategories Listener")
                    var gson = Gson()
                    var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                    mList = categoryResponse.data

                },
                Response.ErrorListener {
                    Log.d("myApp", "getCategories ErrorListener")
                    var statusCode = it.networkResponse.statusCode
                    var message = it.message
                    Toast.makeText(mContext,"GET CATEGORIES ERROR", Toast.LENGTH_LONG ).show()
                }
            )

            var requestQueue = Volley.newRequestQueue(mContext)
            requestQueue.add(request)

            return mList
        }
    }
}
