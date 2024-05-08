package com.example.mobileappui.http.news

import com.example.mobileappui.dtos.news.NewsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/news")
    fun getAllNews(@Query("page") page: Int): Call<List<NewsDto>>
}