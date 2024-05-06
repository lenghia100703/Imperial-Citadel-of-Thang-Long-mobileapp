package com.example.mobileappui.presentation.home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.mobileappui.R

class MainScreen: AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)


    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val menuView: FragmentContainerView = findViewById(R.id.menu)
        val value = intent.getBooleanExtra("openMenu", false)
        Log.d("MainScreen", "Received value from Intent: $value")
        if (value) {
            menuView.visibility = View.VISIBLE

        } else {
            menuView.visibility = View.GONE
        }

    }


}
