package com.example.mobileappui.route

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.exhibition.ExhibitionDto
import com.example.mobileappui.dtos.news.NewsDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.news.NewsService
import com.example.mobileappui.services.search.SearchService
import retrofit2.Call
import retrofit2.Response

class Search : Fragment() {


    private val searchService: SearchService = ApiClient.searchService
    private var searchCategory = "news"

    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val search: EditText = view.findViewById(R.id.search)
        val btnClear: Button = view.findViewById(R.id.btnClear)
        val watcher = object : TextWatcher {
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
                // Xử lý tìm kiếm

                if (searchCategory == "news") {
                    searchNews(search, view)
                } else {
                    searchExhibition(search, view)
                }
                // Đóng bàn phím ảo
                val inputMethodManager =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(search.windowToken, 0)
                btnClear.visibility = View.GONE
                // Thoát con trỏ khỏi EditText
                search.clearFocus()
                return@OnKeyListener true
            }
            false
        })

        val hint1: TextView = view.findViewById(R.id.hint1)
        val hint2: TextView = view.findViewById(R.id.hint2)
        editNewsHint(hint1, hint2)
        //search navigation
        val postBtn: TextView = view.findViewById(R.id.leftBtn)
        val artifactBtn: TextView = view.findViewById(R.id.rightBtn)
        postBtn.setOnClickListener {
            postBtn.background = resources.getDrawable(R.drawable.left_searchselect)
            postBtn.setTextColor(Color.White.toArgb())
            artifactBtn.background = resources.getDrawable(R.drawable.right_search)
            artifactBtn.setTextColor(Color.Black.toArgb())
            searchCategory = "news"
            editNewsHint(hint1, hint2)
            view.findViewById<LinearLayout>(R.id.articleList).removeAllViews()
        }
        artifactBtn.setOnClickListener {
            artifactBtn.background = resources.getDrawable(R.drawable.right_searchselect)
            artifactBtn.setTextColor(Color.White.toArgb())
            postBtn.background = resources.getDrawable(R.drawable.left_search)
            postBtn.setTextColor(Color.Black.toArgb())
            searchCategory = "exhibition"
            editExhibitionHint(hint1, hint2)
            view.findViewById<LinearLayout>(R.id.articleList).removeAllViews()
        }

        hint1.setOnClickListener {
            search.setText(hint1.text)
            btnClear.visibility = View.GONE
            if (searchCategory == "news")
                searchNews(search, view)
            else
                searchExhibition(search, view)

        }
        hint2.setOnClickListener {
            search.setText(hint2.text)
            btnClear.visibility = View.GONE
            if (searchCategory == "news")
                searchNews(search, view)
            else
                searchExhibition(search, view)
        }

        val scrollView = view.findViewById<ScrollView>(R.id.scrollViewArticle)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val maxScroll = scrollView.getChildAt(0).height - scrollView.height
            val currentScroll = scrollView.scrollY
            progressBar.progress =
                (currentScroll.toFloat() / maxScroll.toFloat() * progressBar.max).toInt()
        }
        return view
    }

    private fun searchNews(search: EditText, view: View) {
        searchService.searchNews(1, search.text.toString())
            .enqueue(object : retrofit2.Callback<PaginatedDataDto<NewsDto>> {
                override fun onResponse(
                    call: Call<PaginatedDataDto<NewsDto>>,
                    response: Response<PaginatedDataDto<NewsDto>>
                ) {
                    if (response.isSuccessful) {
                        val news = response.body()?.data
                        val linearLayout = view.findViewById<LinearLayout>(R.id.articleList)
                        linearLayout.removeAllViews()
                        for (newsItem in news!!) {
                            createNews(newsItem, linearLayout)
                        }
                    } else {
                        Log.d("Search", "Failed to search news")
                    }
                }

                override fun onFailure(call: Call<PaginatedDataDto<NewsDto>>, t: Throwable) {
                    Log.d("Search", t.message.toString())
                }
            })
    }

    private fun searchExhibition(search: EditText, view: View) {
        searchService.searchExhibition(1, search.text.toString())
            .enqueue(object : retrofit2.Callback<PaginatedDataDto<ExhibitionDto>> {
                override fun onResponse(
                    call: Call<PaginatedDataDto<ExhibitionDto>>,
                    response: Response<PaginatedDataDto<ExhibitionDto>>
                ) {
                    if (response.isSuccessful) {
                        val exhibitions = response.body()?.data
                        val linearLayout = view.findViewById<LinearLayout>(R.id.articleList)
                        linearLayout.removeAllViews()
                        for (exhibition in exhibitions!!) {
                            createExhibition(exhibition, linearLayout)
                        }
                    } else {
                        Log.d("Search", "Failed to search exhibition")
                    }
                }

                override fun onFailure(call: Call<PaginatedDataDto<ExhibitionDto>>, t: Throwable) {
                    Log.d("Search", t.message.toString())
                }
            })
    }

    private fun editNewsHint(hint1: TextView, hint2: TextView) {
        val newsService: NewsService = ApiClient.newsService
        newsService.getAllNews(1).enqueue(object : retrofit2.Callback<PaginatedDataDto<NewsDto>> {
            override fun onResponse(
                call: Call<PaginatedDataDto<NewsDto>>,
                response: Response<PaginatedDataDto<NewsDto>>
            ) {
                if (response.isSuccessful) {
                    val news = response.body()?.data
                    Log.d("News", "These are the news")
                    hint1.text = news!![0].title
                    hint2.text = news[1].title
                } else {
                    Log.d("News", "Failed to get news")
                }
            }

            override fun onFailure(call: Call<PaginatedDataDto<NewsDto>>, t: Throwable) {
                Log.d("News", t.message.toString())
            }
        })
    }

    private fun editExhibitionHint(hint1: TextView, hint2: TextView) {
        hint1.text = "Sưu tập bàn chân"
        hint2.text = "Sưu tập bát"
    }

    //create item for each news
    @SuppressLint("ClickableViewAccessibility")
    private fun createNews(newsItem: NewsDto, layout: LinearLayout) {
        val item = LinearLayout(context)
        item.id = newsItem.id.toInt()
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 20)
        item.layoutParams = layoutParams

        //create to view image and text
        val webView = WebView(requireContext())

        val webViewParams = LinearLayout.LayoutParams(
            200, 200
        )
        webViewParams.setMargins(0, 0, 30, 0)
        webView.layoutParams = webViewParams

        webView.settings.setJavaScriptEnabled(true)
        val htmlContent = """
                                            <html>
                                                <body style="margin:0;padding:0;">
                                            <img src="${newsItem.image}" alt="Image" style="width:100%;height:100%;"/>
                                                </body>
                                            </html>
                                            """
        webView.loadData(htmlContent, "text/html", "UTF-8")

        item.addView(webView)

        val linearInfo = LinearLayout(context)
        linearInfo.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearInfo.orientation = LinearLayout.VERTICAL

        val title = TextView(context)
        title.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        title.text = newsItem.title
        title.textSize = 20f
        title.setTypeface(null, Typeface.BOLD)
        linearInfo.addView(title)

        val description = TextView(context)
        description.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        description.text = newsItem.body
        linearInfo.addView(description)
        item.addView(linearInfo)

        item.setOnClickListener {
            val article = view?.findViewById<ConstraintLayout>(R.id.articleLayout)
            view?.findViewById<TextView>(R.id.articleTitle)?.text = newsItem.title
            view?.findViewById<TextView>(R.id.articleCreateBy)?.text = newsItem.createdBy
            view?.findViewById<TextView>(R.id.articleTime)?.text = newsItem.createdAt.toString()
            view?.findViewById<WebView>(R.id.articleImage)?.loadUrl(newsItem.image)
            view?.findViewById<TextView>(R.id.articleContent)?.text = newsItem.body
            view?.findViewById<LinearLayout>(R.id.searchLayout)?.isEnabled = false
            article?.visibility = View.VISIBLE
            view?.findViewById<Button>(R.id.articleExit)?.setOnClickListener {
                article?.visibility = View.GONE
            }
        }
        layout.addView(item)
    }

    //create item for each exhibition
    private fun createExhibition(exhibition: ExhibitionDto, layout: LinearLayout) {
        val item = LinearLayout(context)
        item.id = exhibition.id.toInt()
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 20)
        item.layoutParams = layoutParams

        //create to view image and text
        val webView = WebView(requireContext())
        val webViewParams = LinearLayout.LayoutParams(
            200, 200
        )
        webViewParams.setMargins(0, 0, 30, 0)
        webView.layoutParams = webViewParams

        webView.settings.setJavaScriptEnabled(true)
        val htmlContent = """
                                            <html>
                                                <body style="margin:0;padding:0;">
                                            <img src="${exhibition.image}" alt="Image" style="width:100%;height:100%;"/>
                                                </body>
                                            </html>
                                            """
        webView.loadData(htmlContent, "text/html", "UTF-8")

        item.addView(webView)

        val linearInfo = LinearLayout(context)
        linearInfo.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        linearInfo.orientation = LinearLayout.VERTICAL

        val title = TextView(context)
        title.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        title.text = exhibition.name
        title.textSize = 20f
        title.setTypeface(null, Typeface.BOLD)
        linearInfo.addView(title)

        val description = TextView(context)
        description.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        description.text = exhibition.description
        linearInfo.addView(description)

        item.addView(linearInfo)

        item.setOnClickListener {
            val article = view?.findViewById<ConstraintLayout>(R.id.articleLayout)
            view?.findViewById<TextView>(R.id.articleTitle)?.text = exhibition.name
            view?.findViewById<TextView>(R.id.articleCreateBy)?.text = exhibition.createdBy
            view?.findViewById<TextView>(R.id.articleTime)?.text = exhibition.createdAt.toString()
            view?.findViewById<WebView>(R.id.articleImage)?.loadUrl(exhibition.image)
            view?.findViewById<TextView>(R.id.articleContent)?.text = exhibition.description
            view?.findViewById<LinearLayout>(R.id.searchLayout)?.isEnabled = false
            article?.visibility = View.VISIBLE
            view?.findViewById<Button>(R.id.articleExit)?.setOnClickListener {
                article?.visibility = View.GONE
            }
        }
        layout.addView(item)
    }
}