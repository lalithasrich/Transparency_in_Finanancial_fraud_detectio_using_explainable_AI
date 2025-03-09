package com.project.creditPay.responsiveLayer

import com.project.creditPay.responsiveLayer.responses.CardResponses
import com.project.creditPay.responsiveLayer.responses.CommonResponse
import com.project.creditPay.responsiveLayer.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @FormUrlEncoded
    @POST("users.php")
    suspend fun createUsers(
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("password") password: String,
    ): Response<CommonResponse>

    @GET("functions.php")
    suspend fun login(
        @Query("mobile") mobile: String,
        @Query("password") password: String,
        @Query("con") con: String = "MyLoginPage",
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("addCard.php")
    suspend fun addCard(
        @Field("cardName") cardName: String,
        @Field("cardNumber") cardNumber: String,
        @Field("expiry") expiry: String,
        @Field("cvv") cvv: String,
        @Field("sessionTime") sessionTime: String,
        @Field("userId") userId: String,
    ): Response<CommonResponse>

    @GET("functions.php")
    suspend fun getMyCards(
        @Query("con") con: String = "getMyCards",
        @Query("id") id: String,
    ): Response<CardResponses>

}
