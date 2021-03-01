package com.android_training.grocery.models

import java.io.Serializable

data class ProductResponse(
    val count: Int,
    val `data`: ArrayList<Product>,
    val error: Boolean
)

data class Product(
    var _id: String? = "",
    var image: String? = "",
    var mrp: Double? = null,
    var price: Double? = null,
    var productName: String? = "",
    var quantity: Int? = null,
    var description: String? = ""
): Serializable{
    companion object{
        const val DATA = "PRODUCT"
    }
}

//data class Product(
//    var __v: Int? = 0,
//    var _id: String? = "",
//    var catId: Int? = null,
//    var created: String? = "",
//    var description: String? = "",
//    var image: String? = "",
//    var mrp: Double? = null,
//    var position: Int? = 1,
//    var price: Double? = null,
//    var productName: String? = "",
//    var quantity: Int? = null,
//    var status: Boolean? = true,
//    var subId: Int? = null,
//    var unit: String? = ""
//): Serializable{
//    companion object{
//        const val DATA = "PRODUCT"
//    }
//}