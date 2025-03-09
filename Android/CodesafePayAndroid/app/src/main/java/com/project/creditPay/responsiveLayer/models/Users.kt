package com.project.creditPay.responsiveLayer.models

import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName

data class Users(
    @DocumentId var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("password") var password: String? = null,
)
