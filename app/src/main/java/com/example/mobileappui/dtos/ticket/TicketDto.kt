package com.example.mobileappui.dtos.ticket

import com.example.mobileappui.dtos.answer.AnswerDto
import com.example.mobileappui.dtos.transaction.TransactionDto
import java.util.Date

data class TicketDto(
    val id: Long,
    val totalPrice: Long,
    val expiry: Date,
    val transactionId: TransactionDto,
    val createdBy: String,
    val updatedBy: String,
    val createdAt: Date,
    val updatedAt: Date,
)
