package com.example.mobileappui.dtos.common

data class CommonResponseDto<T>(
    val code: String = "SUCCESS",
    val data: T,
)
