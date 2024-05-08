package com.example.mobileappui.dtos.banner

import java.util.Date

data class BannerDto(
    val id: Long,
    val title: String,
    val isActive: String,
    val image: String,
    val createdBy: String,
    val updatedBy: String,
    val createdAt: Date,
    val updatedAt: Date,
)
