package com.example.mobileappui.dtos.user

data class ChangePasswordDto(
    val currentPassword: String,
    val password: String,
    val confirmPassword: String
)
