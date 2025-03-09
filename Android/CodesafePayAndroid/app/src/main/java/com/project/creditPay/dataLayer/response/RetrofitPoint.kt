package com.project.creditPay.dataLayer.response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitPoint {
    private val BASEURL = "http://192.168.1.212:5000/"

    val api by lazy {
        Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(Api::class.java)
    }
}