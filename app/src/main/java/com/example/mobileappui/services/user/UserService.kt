package com.example.mobileappui.services.user

import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.user.ChangePasswordDto
import com.example.mobileappui.dtos.user.UserDto
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserService {
    @GET("user/me")
    fun getCurrentUser(): Call<UserDto>

    @GET("user/{id}")
    fun getUserById(@Path("id") id: Long): Call<CommonResponseDto<UserDto>>

    @Multipart
    @PUT("user/{id}")
    fun editUser(@Path("id") id: Long, @Part("username") username: RequestBody, @Part("email") email: RequestBody,
                 @Part("phone") phone: RequestBody,
                 @Part("avatarUrl") avatarUrl: RequestBody,
                 @Part("avatar") avatar: RequestBody?
                 ): Call<CommonResponseDto<String>>

    @PUT("user/change-password/{id}")
    fun changePassword(@Path("id") id: Long, @Body changePasswordDto: ChangePasswordDto): Call<CommonResponseDto<String>>
}