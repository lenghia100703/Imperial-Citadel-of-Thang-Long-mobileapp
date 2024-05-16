package com.example.mobileappui.services.user

import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.user.ChangePasswordDto
import com.example.mobileappui.dtos.user.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @GET("/user/me")
    fun getCurrentUser(): Call<UserDto>

    @GET("user/{id}")
    fun getUserById(@Path("id") id: Long): Call<CommonResponseDto<UserDto>>

    @PUT("user/{id}")
    fun editUser(@Path("id") id: Long): Call<String>

    @PUT("user/change-password/{id}")
    fun changePassword(@Path("id") id: Long, @Body changePasswordDto: ChangePasswordDto): Call<String>
}