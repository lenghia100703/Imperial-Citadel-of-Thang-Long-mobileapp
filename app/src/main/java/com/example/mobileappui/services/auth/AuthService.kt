package com.example.mobileappui.services.auth

import com.example.mobileappui.dtos.auth.AuthResponseDto
import com.example.mobileappui.dtos.auth.LoginDto
import com.example.mobileappui.dtos.auth.RegisterDto
import com.example.mobileappui.dtos.user.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/auth/login")
    fun login(@Body loginDto: LoginDto): Call<AuthResponseDto>

    @POST("/auth/register")
    fun register(@Body registerDto: RegisterDto): Call<UserDto>

    @POST("/auth/logout")
    fun logout(): Call<String>
}