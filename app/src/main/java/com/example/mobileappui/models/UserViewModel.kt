package com.example.mobileappui.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.user.ChangePasswordDto
import com.example.mobileappui.dtos.user.UserDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.user.UserService
import com.example.mobileappui.utils.PreferencesHelper
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userService: UserService = ApiClient.userService
    private val preferencesHelper = PreferencesHelper(application)

    private val _user = MutableLiveData<UserDto>()
    val user: LiveData<UserDto> get() = _user

    fun getUserById(onError: () -> Unit) {
        userService.getUserById(preferencesHelper.userId).enqueue(object : retrofit2.Callback<CommonResponseDto<UserDto>> {
            override fun onResponse(call: Call<CommonResponseDto<UserDto>>, response: Response<CommonResponseDto<UserDto>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    if (responseData != null) {
                        val userModel = UserDto(
                            id = responseData.id,
                            username = responseData.username,
                            email = responseData.email,
                            phone = responseData.phone,
                            role = responseData.role,
                            avatar = responseData.avatar,
                            createdAt = responseData.createdAt,
                            createdBy = responseData.createdBy,
                            updatedAt = responseData.updatedAt,
                            updatedBy = responseData.updatedBy,
                        )
                        _user.postValue(userModel)
                        Log.d("UserViewModel", "onResponse: $responseData")
                    } else {
                        Log.d("UserViewModel", "onResponse: $responseData")
                        onError()
                    }
                } else {
                    Log.d("UserViewModel", "onResponse: ${response.message()}")
                    onError()
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<UserDto>>, t: Throwable) {
                Log.d("UserViewModel", "onFailure: ${t.message}")
                onError()
            }

        })
    }

    fun changePassword(currentPassword: String, password: String, confirmPassword: String, onSuccess: () -> Unit, onError: () -> Unit) {
        userService.changePassword(preferencesHelper.userId, ChangePasswordDto(currentPassword, password, confirmPassword)).enqueue(object : retrofit2.Callback<CommonResponseDto<String>> {
            override fun onResponse(call: Call<CommonResponseDto<String>>, response: Response<CommonResponseDto<String>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    if (responseData != null) {
                        onSuccess()
                        Log.d("UserViewModel", "onResponse: $responseData")
                    } else {
                        Log.d("UserViewModel", "onResponse: $responseData")
                        onError()
                    }
                } else {
                    Log.d("UserViewModel", "onResponse: ${response.message()}")
                    onError()
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<String>>, t: Throwable) {
                Log.d("UserViewModel", "onFailure: ${t.message}")
                onError()
            }

        })
    }

    fun editUser(username: String, phone: String, avatarUrl: String, onSuccess: () -> Unit, onError: () -> Unit) {
        userService.getUserById(preferencesHelper.userId).enqueue(object : retrofit2.Callback<CommonResponseDto<UserDto>> {
            override fun onResponse(call: Call<CommonResponseDto<UserDto>>, response: Response<CommonResponseDto<UserDto>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    if (responseData != null) {
                        val usernameBody = username.toRequestBody("text/plain".toMediaTypeOrNull())
                        val emailBody =
                            responseData.email.toRequestBody("text/plain".toMediaTypeOrNull())
                        val phoneBody = phone.toRequestBody("text/plain".toMediaTypeOrNull())
                        val avatarUrlBody =
                            (avatarUrl ?: "").toRequestBody("text/plain".toMediaTypeOrNull())

                        userService.editUser(preferencesHelper.userId, usernameBody, emailBody, phoneBody, avatarUrlBody, null).enqueue(object : retrofit2.Callback<CommonResponseDto<String>> {
                            override fun onResponse(call: Call<CommonResponseDto<String>>, response: Response<CommonResponseDto<String>>) {
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

                            override fun onFailure(call: Call<CommonResponseDto<String>>, t: Throwable) {
                                Log.d("UserViewModel", "onFailure: ${t.message}")
                                onError()
                            }

                        })
                        Log.d("UserViewModel", "onResponse: $responseData")
                    } else {
                        Log.d("UserViewModel", "onResponse: $responseData")
                        onError()
                    }
                } else {
                    Log.d("UserViewModel", "onResponse: ${response.message()}")
                    onError()
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<UserDto>>, t: Throwable) {
                Log.d("UserViewModel", "onFailure: ${t.message}")
                onError()
            }

        })

    }
}