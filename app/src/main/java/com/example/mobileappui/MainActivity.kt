package com.example.mobileappui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileappui.presentation.home.HomeScreen



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
    }

}
