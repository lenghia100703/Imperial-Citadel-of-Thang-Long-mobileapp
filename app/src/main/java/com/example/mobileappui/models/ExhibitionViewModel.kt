package com.example.mobileappui.models

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobileappui.dtos.auth.AuthResponseDto
import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.exhibition.ExhibitionDto
import com.example.mobileappui.dtos.user.UserDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.exhibition.ExhibitionService
import retrofit2.Call
import retrofit2.Response
import java.util.Date

class ExhibitionViewModel(application: Application) : AndroidViewModel(application) {
    private val _exhibitionItems = MutableLiveData<List<ExhibitionDto>?>()
    private val exhibitionService: ExhibitionService = ApiClient.exhibitionService
    val exhibitionItems: MutableLiveData<List<ExhibitionDto>?> get() = _exhibitionItems

    init {
        loadExhibitionItems()
    }

    private fun loadExhibitionItems() {
//        val userDto: UserDto = UserDto(1, "lenghia", "lenghia@gmail.com", "0313192", "httpasd", "ADMIN", "lenghia1007@gmail.com", "lenghia1007@gmail.com", Date(System.currentTimeMillis()), Date(System.currentTimeMillis()))
//        _exhibitionItems.value = listOf(
//            ExhibitionDto(1, "Name 1", "Description 1", "https://httl.maiatech.com.vn//Content/Pictures/HV/11499/Picture1.png", userDto, "lenghia1007@gmail.com", "lenghia1007@gmail.com", Date(System.currentTimeMillis()), Date(System.currentTimeMillis())),
//            ExhibitionDto(2, "Name 2", "Description 2", "https://httl.maiatech.com.vn//Content/Pictures/HV/11499/Picture1.png", userDto, "lenghia1007@gmail.com", "lenghia1007@gmail.com", Date(System.currentTimeMillis()), Date(System.currentTimeMillis())),
//            ExhibitionDto(3, "Name 3", "Description 3", "https://httl.maiatech.com.vn//Content/Pictures/HV/11499/Picture1.png", userDto, "lenghia1007@gmail.com", "lenghia1007@gmail.com", Date(System.currentTimeMillis()), Date(System.currentTimeMillis())),
//            ExhibitionDto(4, "Name 4", "Description 4", "https://httl.maiatech.com.vn//Content/Pictures/HV/11499/Picture1.png", userDto, "lenghia1007@gmail.com", "lenghia1007@gmail.com", Date(System.currentTimeMillis()), Date(System.currentTimeMillis())),
//            )

        exhibitionService.getAllExhibition(0).enqueue(object : retrofit2.Callback<PaginatedDataDto<ExhibitionDto>> {
            override fun onResponse(call: Call<PaginatedDataDto<ExhibitionDto>>, response: Response<PaginatedDataDto<ExhibitionDto>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()?.data
                    if (responseData != null) {
                        Log.d("ExhibitionViewModel", "onResponse: ${responseData.toString()}")
                        _exhibitionItems.value = responseData
                    } else {
                        Log.d("ExhibitionViewModel", "onResponseNull: $responseData")
                    }
                } else {
                    Log.d("ExhibitionViewModel", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PaginatedDataDto<ExhibitionDto>>, t: Throwable) {
                Log.d("ExhibitionViewModel", "onFailure: ${t.message}")
            }

        })
    }
}