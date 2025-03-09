package com.project.creditPay.responsiveLayer.models

import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName

data class CardDetails (

    @DocumentId var id          : String? = null,
    @SerializedName("cardName"    ) var cardName    : String? = null,
    @SerializedName("cardNumber"  ) var cardNumber  : String? = null,
    @SerializedName("expiry"      ) var expiry      : String? = null,
    @SerializedName("cvv"         ) var cvv         : String? = null,
    @SerializedName("sessionTime" ) var sessionTime : String? = null,
    @SerializedName("userId"      ) var userId      : String? = null,
    @SerializedName("balanceOf"     ) var balanceOf  : Int? = null
)
