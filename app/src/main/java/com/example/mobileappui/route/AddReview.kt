package com.example.mobileappui.route

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.mobileappui.MainActivity
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.CommonResponseDto
import com.example.mobileappui.dtos.post.PostDto
import com.example.mobileappui.models.AuthViewModel
import com.example.mobileappui.models.PostViewModel
import com.example.mobileappui.models.UserViewModel
import com.example.mobileappui.presentation.profile.ProfileFragment
import com.example.mobileappui.retrofit.ApiClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddReview<File> : Fragment() {
    private lateinit var returnButton: Button
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etImage: EditText
    private lateinit var submitButton: Button
    private val viewModel: PostViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add_review, container, false)
        etTitle = view.findViewById(R.id.etTitle)
        etDescription = view.findViewById(R.id.etDescription)
        etImage = view.findViewById(R.id.etImage)
        returnButton = view.findViewById(R.id.back_btn)
        returnButton.setOnClickListener {
            replaceFragment(ProfileFragment())
        }

        submitButton = view.findViewById(R.id.btnSubmit)
        submitButton.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDescription.text.toString()
            val imgUrl = etImage.text.toString()
            val titleBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
            val descBody = desc.toRequestBody("text/plain".toMediaTypeOrNull())
            val imgUrlBody = imgUrl.toRequestBody("text/plain".toMediaTypeOrNull())
            viewModel.createPost(null, imgUrlBody, titleBody ,descBody,
                {
                    Toast.makeText(
                        requireContext(),
                        "Upload thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                {
                    Toast.makeText(
                        requireContext(),
                        "Upload thất bại!",
                        Toast.LENGTH_SHORT
                    ).show()
                })
            replaceFragment(ReviewList(0))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}