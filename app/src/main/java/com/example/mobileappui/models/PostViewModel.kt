package com.example.mobileappui.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.post.PostDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.post.PostService
import com.example.mobileappui.services.user.UserService
import com.example.mobileappui.utils.PreferencesHelper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val postService: PostService = ApiClient.postService
    private val preferencesHelper = PreferencesHelper(application)

    fun createPost(image: RequestBody?, imageUrl: RequestBody, title: RequestBody, description: RequestBody ,onSuccess: () -> Unit, onError: () -> Unit) {
        postService.createPost(image, preferencesHelper.userId.toString().toRequestBody("text/plain".toMediaTypeOrNull()) ,title, description, imageUrl).enqueue(object : retrofit2.Callback<CommonResponseDto<PostDto>> {
            override fun onResponse(call: Call<CommonResponseDto<PostDto>>, response: Response<CommonResponseDto<PostDto>>) {
                if (response.isSuccessful) {
                    val newResponse = response.body()?.data
                    if (newResponse != null) {
                        onSuccess()
                        Log.d("UserViewModel", "onResponse: $newResponse")
                    } else {
                        Log.d("UserViewModel", "onResponse: $newResponse")
                        onError()
                    }
                } else {
                    Log.d("UserViewModel", "onResponse: ${response.message()}")
                    onError()
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<PostDto>>, t: Throwable) {
                Log.d("UserViewModel", "onFailure: ${t.message}")
                onError()
            }
        })
    }
}