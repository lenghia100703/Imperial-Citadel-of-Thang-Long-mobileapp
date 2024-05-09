package com.example.mobileappui.services.ticket

import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.ticket.TicketDto
import retrofit2.Call
import retrofit2.http.GET

interface TicketService {
    @GET("/ticket/me")
    fun getTicketByUserId(): Call<CommonResponseDto<List<TicketDto>>>

}