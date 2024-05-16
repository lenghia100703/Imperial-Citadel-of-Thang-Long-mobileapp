package com.example.mobileappui.dtos.user

import java.util.Date

data class UserDto(
    val id: Long,
    val username: String,
    val email: String,
    val phone: String?,
    val avatar: String,
    val role: String,
    val createdBy: String,
    val updatedBy: String?,
    val createdAt: Date,
    val updatedAt: Date?,
)
