package com.example.mobileappui.presentation.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.mobileappui.MainActivity
import com.example.mobileappui.R
import com.example.mobileappui.models.AuthViewModel
import com.example.mobileappui.presentation.register.RegisterFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val register = view.findViewById<TextView>(R.id.register)

        loginButton.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.email).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()

            viewModel.login(email, password,
                {
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                    Toast.makeText(
                        requireContext(),
                        "Đăng nhập thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                {
                    Toast.makeText(
                        requireContext(),
                        "Đăng nhập thất bại!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        register.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

    }
}