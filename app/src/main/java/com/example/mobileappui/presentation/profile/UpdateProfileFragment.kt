package com.example.mobileappui.presentation.profile

import android.content.Intent
import android.os.Bundle
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
import com.example.mobileappui.models.UserViewModel

class UpdateProfileFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val updateProfileButton = view.findViewById<Button>(R.id.registerButton)
        val backBtn = view.findViewById<Button>(R.id.back_btn)

        updateProfileButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.username).text.toString()
            val phone = view.findViewById<EditText>(R.id.phone).text.toString()
            val avatarUrl = view.findViewById<EditText>(R.id.image).text.toString()

            userViewModel.editUser(username, phone, avatarUrl,
                {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                    Toast.makeText(
                        requireContext(),
                        "Đổi thông tin thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                {
                    Toast.makeText(
                        requireContext(),
                        "Đổi thông tin thất bại!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        backBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, ProfileFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}