package com.android_training.grocery.app

class Endpoints {
    companion object{
        private const val URL_CATEGORY = "category"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val URL_PRODUCT = "products/sub/"
        private const val URL_REGISTER = "auth/register"
        private const val URL_LOGIN = "auth/login"
        private const val URL_ADDRESS = "address"
        private const val URL_ORDERS = "orders"

        fun getCategory():String{
            return "${Config.BASE_URL + URL_CATEGORY}"
        }
        fun getSubCategoryById(catId: Int):String{
            return "${Config.BASE_URL + URL_SUB_CATEGORY + catId.toString()}"
        }
        fun getProductBySubId(subId: Int):String{
            return "${Config.BASE_URL + URL_PRODUCT + subId.toString()}"
        }
        fun getRegister():String{
            return "${Config.BASE_URL + URL_REGISTER}"
        }
        fun getLogin():String{
            return "${Config.BASE_URL + URL_LOGIN}"
        }
        fun getAddress():String{
            return "${Config.BASE_URL + URL_ADDRESS}"
        }
        fun getAddressById(id: String):String{
            return "${Config.BASE_URL + URL_ADDRESS + "/"+id}"
        }
        fun getOrders():String{
            return "${Config.BASE_URL + URL_ORDERS}"
        }
        fun getOrdersByUserId(userId: String):String{
            return "${Config.BASE_URL + URL_ORDERS + "/"+userId}"
        }
    }
}