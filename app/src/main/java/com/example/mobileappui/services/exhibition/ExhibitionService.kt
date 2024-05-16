package com.example.mobileappui.services.exhibition

import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.exhibition.ExhibitionDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExhibitionService {
    @GET("exhibition")
    fun getAllExhibition(@Query("page") page: Int): Call<PaginatedDataDto<ExhibitionDto>>
}