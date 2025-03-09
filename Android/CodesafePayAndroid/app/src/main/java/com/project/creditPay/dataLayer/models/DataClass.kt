package com.project.creditPay.dataLayer.models

import com.google.firebase.firestore.DocumentId

data class DataClass(
    @DocumentId var transactionID: String = "CSPA1",
    var transactionName: String = "Charan . M",
    var transType: Boolean = false,
    var amount: String = "10,00000",
    var time: String = "27/01/2002",
    var state: String = "Pending",
    var from: String = "",
    var to: String = ""
)