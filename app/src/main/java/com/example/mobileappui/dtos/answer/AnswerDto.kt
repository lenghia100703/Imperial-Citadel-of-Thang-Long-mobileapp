package com.example.mobileappui.dtos.answer

import java.util.Date

data class AnswerDto(
    val id: Long,
    val answer1: String,
    val answer2: String,
    val answer3: String,
    val answer4: String,
    val createdBy: String,
    val updatedBy: String,
    val createdAt: Date,
    val updatedAt: Date,
)
