package com.example.mobileappui.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.mobileappui.MainActivity
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.user.UserDto
import com.example.mobileappui.models.AuthViewModel
import com.example.mobileappui.models.UserViewModel
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.user.UserService
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val userService: UserService = ApiClient.userService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameTextView = view.findViewById<TextView>(R.id.username)!!;
        val emailTextView = view.findViewById<TextView>(R.id.email)!!;
        val phoneTextView = view.findViewById<TextView>(R.id.phone)!!;
        val roleTextView = view.findViewById<TextView>(R.id.role)!!;
        val profileImageView = view.findViewById<ImageView>(R.id.profile_image)!!;

        userViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            usernameTextView.text = user.username
            emailTextView.text = user.email
            if (user.phone != null) {
                phoneTextView.text = user.phone
            } else {
                phoneTextView.text = ""
            }

            roleTextView.text = user.role

            Glide.with(this)
                .load(user.avatar)
                .into(profileImageView)
        })

        userViewModel.getUserById {
            Toast.makeText(
                requireContext(),
                "Lấy hồ sơ người dùng thất bại!",
                Toast.LENGTH_SHORT
            ).show()
        };

        val btn = view.findViewById<Button>(R.id.click_btn)
        btn.setOnClickListener {
            viewModel.logout(
                {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                    Toast.makeText(
                        requireContext(),
                        "Đăng xuất thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                {
                    Toast.makeText(
                        requireContext(),
                        "Đăng xuất thất bại!",
                        Toast.LENGTH_SHORT
                    ).show()
                })
        }
    }

//    private fun getUserProfile(id: Long, onSuccess: (String) -> Unit, onError: () -> ) {
//        userService.getUserById(id).enqueue(object : retrofit2.Callback<CommonResponseDto<UserDto>> {
//            override fun onResponse(call: Call<CommonResponseDto<UserDto>>, response: Response<CommonResponseDto<UserDto>>) {
//                if (response.isSuccessful) {
//                    val responseData = response.body()?.data
//                    if (responseData != null) {
//                        onSuccess(responseData.toString())
//                        usernameTextView.text = responseData.username;
//                        emailTextView.text = responseData.email;
//                        phoneTextView.text = responseData.phone;
//                        roleTextView.text = responseData.role;
//
//                        context?.let {
//                            Glide.with(it)
//                                .load(responseData.avatar)
//                                .into(profileImageView)
//                        };
//                        Log.d("UserViewModel", "onResponse: $responseData")
//                    } else {
//                        Log.d("UserViewModel", "onResponse: $responseData")
//                        onError()
//                    }
//                } else {
//                    Log.d("UserViewModel", "onResponse: ${response.message()}")
//                    onError()
//                }
//            }
//
//            override fun onFailure(call: Call<CommonResponseDto<UserDto>>, t: Throwable) {
//                Log.d("UserViewModel", "onFailure: ${t.message}")
//                onError()
//            }
//
//        })
//    }
}