package com.example.mobileappui.services.search

import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.exhibition.ExhibitionDto
import com.example.mobileappui.dtos.news.NewsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/news")
    fun searchNews(@Query("page") page: Int, @Query("q") q: String): Call<PaginatedDataDto<NewsDto>>

    @GET("search/exhibition")
    fun searchExhibition(@Query("page") page: Int, @Query("q") q: String): Call<PaginatedDataDto<ExhibitionDto>>
}