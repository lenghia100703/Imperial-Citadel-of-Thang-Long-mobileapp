package com.example.mobileappui.presentation.viewArticle

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.ScrollView
import androidx.fragment.app.Fragment

import com.example.mobileappui.R

class News : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.article_info, container, false)
        val scrollView = view.findViewById<ScrollView>(R.id.scrollViewArticle)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollY = scrollView.scrollY
            val maxScroll = scrollView.getChildAt(0).height - scrollView.height
            val progress = scrollY.toFloat() / maxScroll.toFloat()
            progressBar.progress = (progress * progressBar.max).toInt()
        }
        return view
    }

}