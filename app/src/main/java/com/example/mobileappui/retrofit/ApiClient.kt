package com.example.mobileappui.retrofit

import com.example.mobileappui.services.auth.AuthService
import com.example.mobileappui.services.banner.BannerService
import com.example.mobileappui.services.news.NewsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val host = "192.168.0.2" // check in terminal run command ipconfig -> host = ipv4 address
    private const val apiURL = "http://${host}:8888/api"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsService: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }

    val bannerService: BannerService by lazy {
        retrofit.create(BannerService::class.java)
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
}