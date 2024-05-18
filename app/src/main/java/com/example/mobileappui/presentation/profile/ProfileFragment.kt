package com.example.mobileappui.presentation.profile

import android.os.Bundle
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
import com.example.mobileappui.R
import com.example.mobileappui.models.AuthViewModel
import com.example.mobileappui.models.UserViewModel
import com.example.mobileappui.presentation.login.LoginFragment
import com.example.mobileappui.route.AddReview
import com.example.mobileappui.presentation.password.PasswordFragment
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.user.UserService

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
        val profileImageView = view.findViewById<ImageView>(R.id.profile_image)!!

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
        val changePassword = view.findViewById<Button>(R.id.change_password)
        val updateProfile = view.findViewById<Button>(R.id.update_profile)
        val addReview = view.findViewById<Button>(R.id.add_review)
        changePassword.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, PasswordFragment())
                .addToBackStack(null)
                .commit()
        }
        addReview.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, AddReview<Any>())
                .addToBackStack(null)
                .commit()
        }
        updateProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, UpdateProfileFragment())
                .addToBackStack(null)
                .commit()
        }

        btn.setOnClickListener {
            viewModel.logout(
                {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, LoginFragment())
                        .addToBackStack(null)
                        .commit()
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

}