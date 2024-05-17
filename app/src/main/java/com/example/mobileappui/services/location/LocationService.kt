package com.example.mobileappui.services.location

import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.location.LocationDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("location")
    fun getAllLocation(@Query("page") page: Int): Call<PaginatedDataDto<LocationDto>>
}