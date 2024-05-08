package com.example.mobileappui.dtos.auth

data class AuthResponseDto(
    val id: Long,
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer "
)
