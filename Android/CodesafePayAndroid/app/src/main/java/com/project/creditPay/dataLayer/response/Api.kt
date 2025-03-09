package com.project.creditPay.dataLayer.response

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("viewPoint/")
    suspend fun checkDetails(
        @Query("income")income:String,
        @Query("customer_age")customer_age:String,
        @Query("housing_status")housing_status:String,
        @Query("phone_mobile_valid")phone_mobile_valid:String,
        @Query("bank_months_count")bank_months_count:String,
        @Query("keep_alive_session")keep_alive_session:String
    ):Response<Predicted>

}
