package com.example.mobileappui.services.ticket

import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.ticket.TicketDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TicketService {
    @GET("ticket/me/{id}")
    fun getTicketByUserId(@Path("id") id: Long): Call<CommonResponseDto<List<TicketDto>>>

    @POST("ticket/{id}")
    fun createTicket(@Path("id") id: Long): Call<CommonResponseDto<TicketDto>>
}