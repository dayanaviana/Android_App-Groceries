package com.android_training.grocery.models

import java.io.Serializable

data class AddressResponse(
    val count: Int,
    val `data`: ArrayList<Address>,
    val error: Boolean
)

data class AddressPostResponse(
    val `data`: Address,
    val error: Boolean,
    val message: String
)

data class Address(
    var _id: String? = "",
    var city: String? = "",
    var houseNo: String? = "",
    var pincode: Int? = 0,
    var streetName: String? = "",
    var type: String? = "",
    var userId: String? = "",
    var __v: Int? = 0
): Serializable {
    companion object{
        const val DATA = "ADDRESS"
    }
}