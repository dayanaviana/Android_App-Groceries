package com.android_training.grocery.models

import java.io.Serializable

data class OrderResponse(
    val `data`: OrderData? = null,
    val error: Boolean,
    val message: String
): Serializable {
    companion object{
        const val DATA = "ORDER_RESPONSE"
    }
}

data class OrderHistoryResponse(
    val `data`: ArrayList<OrderData>? = null,
    val error: Boolean,
    val message: String?= "",
    val count: Int? = 0
): Serializable {
    companion object{
        const val DATA = "ORDER_HISTORY"
    }
}

data class OrderData(
    val __v: Int? = null,
    val _id: String? = null,
    val date: String? = null,
    val orderSummary: OrderSummary,
    val payment: OrderPayment,
    val products: ArrayList<Product>,
    val shippingAddress: Address,
    val user: User,
    val userId: String
): Serializable {
    companion object{
        const val DATA = "ORDER_DATA"
    }
}

data class OrderSummary(
    val _id: String? = null,
    val deliveryCharges: Double,
    val discount: Double,
    val orderAmount: Double,
    val ourPrice: Double,
    val totalAmount: Double
): Serializable {
    companion object{
        const val DATA = "ORDER_SUMMARY"
    }
}

data class OrderPayment(
    val _id: String? = null,
    val paymentMode: String,
    val paymentStatus: String
): Serializable {
    companion object{
        const val DATA = "ORDER_PAYMENT"
    }
}

//data class ProductOrder(
//    val _id: String,
//    val image: String,
//    val mrp: Double,
//    val price: Double,
//    val productName: String,
//    val quantity: Int
//)
