package com.project.creditPay.responsiveLayer

import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitC {
    private val baseUrl = "https://www.wizzie.online/creditCard/"
    val api by lazy {
        retrofit2.Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(
                okhttp3.OkHttpClient.Builder().addInterceptor {
                        it.withReadTimeout(1, TimeUnit.MINUTES)
                        it.withWriteTimeout(1, TimeUnit.MINUTES)
                        it.withConnectTimeout(1, TimeUnit.MINUTES)
                        it.proceed(it.request())
                    }.build()
            ).build().create(
                Api::class.java)


    }
}