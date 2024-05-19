package com.example.mobileappui.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.exhibition.ExhibitionDto
import com.example.mobileappui.dtos.ticket.TicketDto
import com.example.mobileappui.dtos.transaction.AddTransactionDto
import com.example.mobileappui.dtos.transaction.TransactionDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.ticket.TicketService
import com.example.mobileappui.services.transaction.TransactionService
import com.example.mobileappui.services.user.UserService
import com.example.mobileappui.utils.PreferencesHelper
import retrofit2.Call
import retrofit2.Response

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val transactionService: TransactionService = ApiClient.transactionService
    private val ticketService: TicketService = ApiClient.ticketService
    private val _transactions = MutableLiveData<List<TransactionDto>?>()
    val transactions: MutableLiveData<List<TransactionDto>?> get() = _transactions
    private val preferencesHelper = PreferencesHelper(application)

    init {
        getTransactionByUserId()
    }

     fun getTransactionByUserId() {
        transactionService.getTransactionByUserId(preferencesHelper.userId).enqueue(object : retrofit2.Callback<CommonResponseDto<List<TransactionDto>>> {
            override fun onResponse(call: Call<CommonResponseDto<List<TransactionDto>>>, response: Response<CommonResponseDto<List<TransactionDto>>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    if (responseData != null) {
                        _transactions.value = responseData
                        Log.d("TransactionViewModel", "onResponse: $responseData")
                    } else {
                        Log.d("TransactionViewModel", "onResponse: $responseData")
                    }
                } else {
                    Log.d("TransactionViewModel", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<List<TransactionDto>>>, t: Throwable) {
                Log.d("TransactionViewModel", "onFailure: ${t.message}")
            }

        })
    }

    fun createTransactionByUserId(quantity: Long, onSuccess: () -> Unit, onError: () -> Unit) {
        transactionService.createTransactionByUserId(preferencesHelper.userId, AddTransactionDto(quantity)).enqueue(object : retrofit2.Callback<CommonResponseDto<TransactionDto>> {
            override fun onResponse(call: Call<CommonResponseDto<TransactionDto>>, response: Response<CommonResponseDto<TransactionDto>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    if (responseData != null) {
                        ticketService.createTicket(responseData.id).enqueue(object : retrofit2.Callback<CommonResponseDto<TicketDto>> {
                            override fun onResponse(call: Call<CommonResponseDto<TicketDto>>, response: Response<CommonResponseDto<TicketDto>>) {
                                if (response.isSuccessful) {
                                    val responseData = response.body()?.data
                                    if (responseData != null) {
                                        onSuccess()
                                        Log.d("TicketViewModel", "onResponse: $responseData")
                                    } else {
                                        Log.d("TicketViewModel", "onResponse: $responseData")
                                        onError()
                                    }
                                } else {
                                    Log.d("TicketViewModel", "onResponse: ${response.message()}")
                                    onError()
                                }
                            }

                            override fun onFailure(call: Call<CommonResponseDto<TicketDto>>, t: Throwable) {
                                Log.d("TicketViewModel", "onFailure: ${t.message}")
                                onError()
                            }

                        })
                        Log.d("TransactionViewModel", "onResponse: $responseData")
                    } else {
                        Log.d("TransactionViewModel", "onResponse: $responseData")
                        onError()
                    }
                } else {
                    Log.d("TransactionViewModel", "onResponse: ${response.message()}")
                    onError()
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<TransactionDto>>, t: Throwable) {
                Log.d("TransactionViewModel", "onFailure: ${t.message}")
                onError()
            }

        })
    }
}