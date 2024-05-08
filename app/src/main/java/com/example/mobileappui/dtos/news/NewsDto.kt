package com.example.mobileappui.dtos.news

import java.util.Date

data class NewsDto(
    val id: Long,
    val title: String,
    val body: String,
    val image: String,
    val createdBy: String,
    val updatedBy: String,
    val createdAt: Date,
    val updatedAt: Date,
)
