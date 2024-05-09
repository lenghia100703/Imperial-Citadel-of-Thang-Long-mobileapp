package com.example.mobileappui.services.question

import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.question.QuestionDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionService {
    @GET("/question")
    fun getAllQuestion(@Query("page") page: Int): Call<PaginatedDataDto<QuestionDto>>

}