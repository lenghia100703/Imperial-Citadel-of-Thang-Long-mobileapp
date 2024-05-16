package com.example.mobileappui.presentation.register

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
import com.example.mobileappui.R
import com.example.mobileappui.presentation.login.LoginFragment
import com.example.mobileappui.models.AuthViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerButton = view.findViewById<Button>(R.id.registerButton)
        val loginText = view.findViewById<TextView>(R.id.login_page)

        registerButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.username).text.toString()
            val email = view.findViewById<EditText>(R.id.email).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()
            val confirmPassword = view.findViewById<EditText>(R.id.confirm_password).text.toString()

            viewModel.register(username, email, password, confirmPassword,
                {
                    startActivity(Intent(activity, LoginFragment::class.java))
                    activity?.finish()
                    Toast.makeText(
                        requireContext(),
                        "Đăng ký thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                {
                    Toast.makeText(
                        requireContext(),
                        "Đăng ký thất bại!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        loginText.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, LoginFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}