package com.example.mobileappui.presentation.home

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileappui.R


class RegisterActivity: AppCompatActivity() {
    fun String.isValidEmail() =
        isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    lateinit var firstName: EditText
    lateinit var lastName: EditText
    lateinit var phone: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        firstName = findViewById(R.id.firstName)
        lastName = findViewById(R.id.lastName)
        phone = findViewById(R.id.phone)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        findViewById<Button>(R.id.registerButton).setOnClickListener {
            if (TextUtils.isEmpty(firstName.text.toString())) {
                firstName.error = "Please enter first name"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(lastName.text.toString())) {
                lastName.error = "Please enter last name"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(phone.text.toString())) {
                phone.error = "Please enter phone number"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(email.text.toString())) {
                email.error = "Please enter email"
                return@setOnClickListener
            }
            if (!email.text.toString().isValidEmail()) {
                email.error = "Please enter valid email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password.text.toString())) {
                password.error = "Please enter password"
                return@setOnClickListener
            }
            Toast.makeText(applicationContext, "Registration Successful", Toast.LENGTH_SHORT).show()
        }
    }
}