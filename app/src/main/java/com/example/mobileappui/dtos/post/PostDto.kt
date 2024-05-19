package com.example.mobileappui.dtos.post

import java.util.Date

data class PostDto(
    val id: Long,
    val title: String,
    val image: String,
    val createdBy: String,
    val updatedBy: String,
    val createdAt: Date,
    val updatedAt: Date,
    val description: String,
    val rating: Int
)
