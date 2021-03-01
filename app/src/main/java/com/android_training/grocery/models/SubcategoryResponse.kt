package com.android_training.grocery.models

import java.io.Serializable

data class SubcategoryResponse(
    val count: Int,
    val `data`: ArrayList<Subcategory>,
    val error: Boolean
)

data class Subcategory(
    val __v: Int? = 0,
    val _id: String? = "",
    val catId: Int,
    val position: Int? = 1,
    val status: Boolean? = true,
    val subDescription: String? = "",
    val subId: Int,
    val subImage: String? = "",
    val subName: String
):Serializable{
    companion object{
        const val DATA = "SUBCATEGORY"
    }
}