package com.android_training.grocery.models

import java.io.Serializable

data class UserResponse(
    val token: String,
    val user: User
)

data class User(
    var __v: Int? = 0,
    var _id: String? = "",
    var createdAt: String? = "",
    var email: String? = "",
    var firstName: String? = "",
    var name: String? = "",
    var mobile: String? = "",
    var password: String? = ""
): Serializable {
    companion object{
        const val DATA = "USER"
    }
}