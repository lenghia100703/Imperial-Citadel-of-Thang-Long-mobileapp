package com.example.mobileappui.route

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

import com.example.mobileappui.R

class QnAScreen : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.qna_screen, container, false)
        val answer = "2024"
        val buttons = listOf(
            view.findViewById(R.id.answer1),
            view.findViewById(R.id.answer2),
            view.findViewById(R.id.answer3),
            view.findViewById<Button>(R.id.answer4)
        )
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                if (button.text == answer) {
                    println("true")
                } else {
                    println("false")
                }
            }
        }
        return view
    }

}