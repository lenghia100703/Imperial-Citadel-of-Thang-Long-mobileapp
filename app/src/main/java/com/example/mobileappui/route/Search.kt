package com.example.mobileappui.route

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.fragment.app.Fragment
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.news.NewsDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.news.NewsService
import retrofit2.Call
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [Search.newInstance] factory method to
 * create an instance of this fragment.
 */
class Search : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val search: EditText = view.findViewById(R.id.search)
        val btnClear: Button = view.findViewById(R.id.btnClear)
        val watcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: android.text.Editable?) {
                if (s.toString().isNotEmpty()) {
                    btnClear.visibility = View.VISIBLE
                    btnClear.setOnClickListener {
                        search.text.clear()
                    }
                } else btnClear.visibility = View.GONE

            }

        }
        search.addTextChangedListener(watcher)
        search.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                println("Enteredddddddddddddddddddddddddddd")
                // Đóng bàn phím ảo
                val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(search.windowToken, 0)
                btnClear.visibility = View.GONE
                // Thoát con trỏ khỏi EditText
                search.clearFocus()
                return@OnKeyListener true
            }
            false
        })

        val hint1: TextView = view.findViewById(R.id.hint1)
        hint1.setOnClickListener {
            search.setText(hint1.text)
            btnClear.visibility = View.GONE
        }

        //search navigation
        val postBtn: TextView = view.findViewById(R.id.leftBtn)
        val artifactBtn: TextView = view.findViewById(R.id.rightBtn)
        postBtn.setOnClickListener {
            postBtn.background = resources.getDrawable(R.drawable.left_searchselect)
            postBtn.setTextColor(Color.White.toArgb())
            artifactBtn.background = resources.getDrawable(R.drawable.right_search)
            artifactBtn.setTextColor(Color.Black.toArgb())
        }
        artifactBtn.setOnClickListener {
            artifactBtn.background = resources.getDrawable(R.drawable.right_searchselect)
            artifactBtn.setTextColor(Color.White.toArgb())
            postBtn.background = resources.getDrawable(R.drawable.left_search)
            postBtn.setTextColor(Color.Black.toArgb())
        }
        getNews()
        return view
    }
    private val newsService: NewsService = ApiClient.newsService
    fun getNews() {
            newsService.getAllNews(1).enqueue(object : retrofit2.Callback<PaginatedDataDto<NewsDto>> {
            override fun onResponse(call: Call<PaginatedDataDto<NewsDto>>, response: Response<PaginatedDataDto<NewsDto>>) {
                if (response.isSuccessful) {
                    val news = response.body()?.data
                    println("These are the news")
                    Log.d("News", news.toString())
                } else {
                    Log.d("News", "Failed to get news")
                }
            }

            override fun onFailure(call: Call<PaginatedDataDto<NewsDto>>, t: Throwable) {
                Log.d("News", t.message.toString())
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Search.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Search().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}