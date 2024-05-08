package com.example.mobileappui.http.banner

import com.example.mobileappui.dtos.banner.BannerDto
import retrofit2.Call
import retrofit2.http.GET

interface BannerService {
    @GET("/banner/active")
    fun getALlBannerIsActive(): Call<List<BannerDto>>
}