package com.example.mobileappui.services.transaction

import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.transaction.AddTransactionDto
import com.example.mobileappui.dtos.transaction.TransactionDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionService {
    @GET("transaction/me/{id}")
    fun getTransactionByUserId(@Path("id") id: Long): Call<CommonResponseDto<List<TransactionDto>>>

    @POST("transaction/create-by-user/{id}")
    fun createTransactionByUserId(@Path("id") id: Long, @Body addTransactionDto: AddTransactionDto): Call<CommonResponseDto<TransactionDto>>
}