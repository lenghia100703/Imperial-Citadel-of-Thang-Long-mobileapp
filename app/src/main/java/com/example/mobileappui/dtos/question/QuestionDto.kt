package com.example.mobileappui.dtos.question

import com.example.mobileappui.dtos.answer.AnswerDto
import java.util.Date

data class QuestionDto(
    val id: Long,
    val question: String,
    val correctAnswer: String,
    val answerId: AnswerDto,
    val createdBy: String,
    val updatedBy: String,
    val createdAt: Date,
    val updatedAt: Date,
)
