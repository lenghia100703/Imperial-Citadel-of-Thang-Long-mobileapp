package com.example.mobileappui.presentation.exhibition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileappui.R
import com.example.mobileappui.route.Home

class ExhibitionItemFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exhibition_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val backBtn = view.findViewById<Button>(R.id.back_btn)

        val imageView = view.findViewById<ImageView>(R.id.image)
        val titleTextView = view.findViewById<TextView>(R.id.name)
        val descriptionTextView = view.findViewById<TextView>(R.id.description)

        val args = arguments
        val title = args?.getString("name")
        val description = args?.getString("description")
        val imageUrl = args?.getString("image")

        titleTextView.text = title
        descriptionTextView.text = description
        Glide.with(this).load(imageUrl).into(imageView)


        backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, ExhibitionFragment())
                .addToBackStack(null)
                .commit()
        }

    }
}