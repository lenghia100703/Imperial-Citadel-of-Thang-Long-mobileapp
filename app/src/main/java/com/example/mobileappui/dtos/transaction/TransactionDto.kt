package com.example.mobileappui.dtos.transaction

import com.example.mobileappui.dtos.user.UserDto
import java.util.Date

data class TransactionDto(
    val id: Long,
    val paymentMethod: String,
    val totalPrice: Long,
    val quantity: Long,
    val status: String,
    val currency: String,
    val isDeleted: Boolean,
    val userId: UserDto,
    val orderId: String,
    val createdBy: String,
    val updatedBy: String,
    val createdAt: Date,
    val updatedAt: Date,
)
