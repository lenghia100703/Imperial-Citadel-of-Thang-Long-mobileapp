package com.example.mobileappui.services.post

import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.post.PostDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("/post")
    fun getAllPost(@Query("page") page: Int): Call<PaginatedDataDto<PostDto>>
}