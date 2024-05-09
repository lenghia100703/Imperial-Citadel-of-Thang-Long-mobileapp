package com.example.mobileappui.dtos.location

import java.util.Date

data class LocationDto(
    val id: Long,
    val name: String,
    val description: String,
    val longitude: Double,
    val latitude: Double,
    val createdBy: String,
    val updatedBy: String,
    val createdAt: Date,
    val updatedAt: Date,
)
