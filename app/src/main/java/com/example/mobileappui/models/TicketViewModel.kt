package com.example.mobileappui.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.ticket.TicketDto
import com.example.mobileappui.dtos.transaction.TransactionDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.ticket.TicketService
import com.example.mobileappui.services.transaction.TransactionService
import com.example.mobileappui.utils.PreferencesHelper
import retrofit2.Call
import retrofit2.Response

class TicketViewModel(application: Application) : AndroidViewModel(application) {
    private val ticketService: TicketService = ApiClient.ticketService
    private val _tickets = MutableLiveData<List<TicketDto>?>()
    val tickets: MutableLiveData<List<TicketDto>?> get() = _tickets
    private val preferencesHelper = PreferencesHelper(application)

    init {
        getTicketByUserId()
    }

    fun getTicketByUserId() {
        ticketService.getTicketByUserId(preferencesHelper.userId).enqueue(object : retrofit2.Callback<CommonResponseDto<List<TicketDto>>> {
            override fun onResponse(call: Call<CommonResponseDto<List<TicketDto>>>, response: Response<CommonResponseDto<List<TicketDto>>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    if (responseData != null) {
                        tickets.value = responseData
                        Log.d("TicketViewModel", "onResponse: $responseData")
                    } else {
                        Log.d("TicketViewModel", "onResponse: $responseData")
                    }
                } else {
                    Log.d("TicketViewModel", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<List<TicketDto>>>, t: Throwable) {
                Log.d("TicketViewModel", "onFailure: ${t.message}")
            }

        })
    }

    fun createTicket(transactionId: Long, onSuccess: () -> Unit, onError: () -> Unit) {
        ticketService.createTicket(transactionId).enqueue(object : retrofit2.Callback<CommonResponseDto<TicketDto>> {
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
    }
}