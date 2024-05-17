package com.example.mobileappui.presentation.password

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
import com.example.mobileappui.models.AuthViewModel
import com.example.mobileappui.models.UserViewModel
import com.example.mobileappui.presentation.profile.ProfileFragment
import com.example.mobileappui.presentation.register.RegisterFragment
import com.example.mobileappui.route.Home

class PasswordFragment : Fragment() {
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val changePasswordButton = view.findViewById<Button>(R.id.registerButton)
        val backBtn = view.findViewById<Button>(R.id.back_btn)

        changePasswordButton.setOnClickListener {
            val currentPassword = view.findViewById<EditText>(R.id.current_password).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()
            val confirmPassword = view.findViewById<EditText>(R.id.confirm_password).text.toString()

            userViewModel.changePassword(currentPassword, password, confirmPassword,
                {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                    Toast.makeText(
                        requireContext(),
                        "Đổi mật khẩu thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                {
                    Toast.makeText(
                        requireContext(),
                        "Đổi mật khẩu thất bại!",
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