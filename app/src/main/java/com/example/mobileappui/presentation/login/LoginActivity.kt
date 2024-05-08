package com.example.mobileappui.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileappui.R


class LoginActivity : AppCompatActivity() {
    lateinit var username : EditText
    lateinit var password: EditText
    lateinit var loginButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            if (username.text.toString() == "admin" && password.text.toString() == "admin"
            ) {
                Toast.makeText(applicationContext, "Login Successful", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<TextView>(R.id.register).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}