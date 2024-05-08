package com.example.mobileappui.dtos.auth

data class RegisterDto(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)
