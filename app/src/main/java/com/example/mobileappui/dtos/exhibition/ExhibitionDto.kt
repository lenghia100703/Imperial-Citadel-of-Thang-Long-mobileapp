package com.example.mobileappui.dtos.exhibition

import com.example.mobileappui.dtos.user.UserDto
import java.util.Date

data class ExhibitionDto(
    val id: Long,
    val name: String,
    val description: String,
    val image: String,
    val adminId: UserDto,
    val createdBy: String,
    val updatedBy: String?,
    val createdAt: Date,
    val updatedAt: Date?,
)
