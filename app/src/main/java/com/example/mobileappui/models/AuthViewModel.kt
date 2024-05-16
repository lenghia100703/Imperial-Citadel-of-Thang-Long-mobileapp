package com.example.mobileappui.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.mobileappui.dtos.auth.AuthResponseDto
import com.example.mobileappui.dtos.auth.LoginDto
import com.example.mobileappui.dtos.auth.RegisterDto
import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.user.UserDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.auth.AuthService
import com.example.mobileappui.utils.PreferencesHelper
import retrofit2.Call
import retrofit2.Response

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val authService: AuthService = ApiClient.authService
    private val preferencesHelper = PreferencesHelper(application)
    fun login(email: String, password: String, onSuccess: (String) -> Unit, onError: () -> Unit) {
        authService.login(LoginDto(email, password)).enqueue(object : retrofit2.Callback<CommonResponseDto<AuthResponseDto>> {
            override fun onResponse(call: Call<CommonResponseDto<AuthResponseDto>>, response: Response<CommonResponseDto<AuthResponseDto>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data?.accessToken
                    if (responseData != null) {
                        onSuccess(responseData)
                        Log.d("AuthViewModel", "onResponse: $responseData")
                        response.body()?.data?.let { authResponse ->
                            preferencesHelper.isLoggedIn = true
                            preferencesHelper.authToken = authResponse.accessToken
                            preferencesHelper.userId = authResponse.id
                        }
                        Log.d("AuthViewModel", preferencesHelper.isLoggedIn.toString())
                        preferencesHelper.authToken?.let { Log.d("LoginViewModel", it) }
                    } else {
                        Log.d("AuthViewModel", "onResponse: $responseData")
                        onError()
                    }
                } else {
                    Log.d("AuthViewModel", "onResponse: ${response.message()}")
                    onError()
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<AuthResponseDto>>, t: Throwable) {
                Log.d("AuthViewModel", "onFailure: ${t.message}")
                onError()
            }

        })
    }

    fun logout(onSuccess: () -> Unit, onError: () -> Unit) {
        authService.logout(preferencesHelper.userId).enqueue(object : retrofit2.Callback<CommonResponseDto<String>> {
            override fun onResponse(call: Call<CommonResponseDto<String>>, response: Response<CommonResponseDto<String>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    if (responseData != null) {
                        onSuccess()
                        Log.d("AuthViewModel", responseData)
                        preferencesHelper.isLoggedIn = false
                        preferencesHelper.authToken = null
                        preferencesHelper.userId = 0
                        Log.d("AuthViewModel", preferencesHelper.isLoggedIn.toString())
                    }
                } else {
                    onError()
                    Log.d("AuthViewModel", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<String>>, t: Throwable) {
                onError()
                Log.d("AuthViewModel", "onFailure: ${t.message}")
            }

        })
//        preferencesHelper.isLoggedIn = false
//        Log.d("LoginViewModel", preferencesHelper.isLoggedIn.toString())
    }

    fun register(username: String, email: String, password: String, confirmPassword: String, onSuccess: (String) -> Unit, onError: () -> Unit) {
        authService.register(RegisterDto(username, email, password, confirmPassword)).enqueue(object : retrofit2.Callback<CommonResponseDto<UserDto>> {
            override fun onResponse(call: Call<CommonResponseDto<UserDto>>, response: Response<CommonResponseDto<UserDto>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    if (responseData != null) {
                        onSuccess(responseData.id.toString())
                        Log.d("AuthViewModel", "onResponse: $responseData")
                    } else {
                        Log.d("AuthViewModel", "onResponse: $responseData")
                        onError()
                    }
                } else {
                    Log.d("AuthViewModel", "onResponse: ${response.message()}")
                    onError()
                }
            }

            override fun onFailure(call: Call<CommonResponseDto<UserDto>>, t: Throwable) {
                Log.d("AuthViewModel", "onFailure: ${t.message}")
                onError()
            }

        })
    }
}