package com.android_training.grocery.models

data class RegisterResponse(
    val `data`: Register,
    val error: Boolean,
    val message: String
)

data class Register(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
)