package com.example.mobileappui.presentation.home


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.mobileappui.R

class MainScreen: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
    }
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent) // You need to call this if you want to get the Intent in `onCreate()` later
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val menuView: View = findViewById(R.id.menu)
        val value = intent.getStringExtra("openMenu").toString()
        Log.d("MainScreen", "Received value from Intent: $value")
        if (value == "open") {
            menuView.visibility = View.VISIBLE
        } else {
            menuView.visibility = View.GONE
        }

    }

}
