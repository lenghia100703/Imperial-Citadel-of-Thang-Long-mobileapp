package com.example.mobileappui.services.post

import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.post.PostDto
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @GET("post")
    fun getAllPost(@Query("page") page: Int): Call<PaginatedDataDto<PostDto>>

    @Multipart
    @POST("post")
    fun createPost(@Part("image") image: RequestBody?, @Part("title") title: RequestBody,
                   @Part("description") description: RequestBody,
                   @Part("imageUrl") imageUrl: RequestBody,
                   ): Call<CommonResponseDto<PostDto>>
}
