package com.example.mobileappui.dtos.common

data class PaginatedDataDto<T>(
    val page: Int,
    val totalData: Int,
    val data: List<T>
)
