package com.example.mobileappui.services.paypal

import com.example.mobileappui.dtos.common.CommonResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaypalService {
    @POST("/paypal/create-order/{transactionId}")
    fun createOrder(@Path("transactionId") transactionId: Long): Call<CommonResponseDto<String>>

    @GET("/paypal/capture-order/{transactionId}")
    fun captureOrder(@Path("transactionId") transactionId: Long): Call<CommonResponseDto<String>>
}