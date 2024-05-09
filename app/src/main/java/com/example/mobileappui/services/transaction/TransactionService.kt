package com.example.mobileappui.services.transaction

import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.transaction.TransactionDto
import retrofit2.Call
import retrofit2.http.GET

interface TransactionService {
    @GET("/transaction/me")
    fun getTransactionByUserId(): Call<CommonResponseDto<List<TransactionDto>>>
}