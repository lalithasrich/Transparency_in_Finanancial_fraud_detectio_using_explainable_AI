package com.project.creditPay.responsiveLayer.responses

import com.google.gson.annotations.SerializedName
import com.project.creditPay.responsiveLayer.models.Users

data class LoginResponse (
    @SerializedName("error"   ) var error   : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Users> = arrayListOf()

)
