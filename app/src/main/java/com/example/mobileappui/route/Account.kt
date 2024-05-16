package com.example.mobileappui.route

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.example.mobileappui.R
import com.example.mobileappui.presentation.login.LoginFragment
import com.example.mobileappui.models.AuthViewModel
import com.example.mobileappui.presentation.profile.ProfileFragment
import com.example.mobileappui.utils.PreferencesHelper

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Account.newInstance] factory method to
 * create an instance of this fragment.
 */
class Account : Fragment() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferencesHelper = PreferencesHelper(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return if (isLoggedIn()) {
            inflater.inflate(R.layout.fragment_profile, container, false)
        } else {
            inflater.inflate(R.layout.fragment_login, container, false)
        }


    }

    private fun isLoggedIn(): Boolean {
        return preferencesHelper.isLoggedIn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isLoggedIn()) {
            replaceFragment(ProfileFragment())
        } else {
            replaceFragment(LoginFragment())
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}


