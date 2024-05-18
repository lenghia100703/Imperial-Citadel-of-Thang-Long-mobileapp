package com.example.mobileappui.route

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.news.NewsDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.news.NewsService
import retrofit2.Call
import retrofit2.Response
import kotlin.math.roundToInt

class Home : Fragment() {
    var allNewsView = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val newsService: NewsService = ApiClient.newsService
        newsService.getAllNews(1).enqueue(object : retrofit2.Callback<PaginatedDataDto<NewsDto>> {
            @SuppressLint("MissingInflatedId")
            override fun onResponse(call: Call<PaginatedDataDto<NewsDto>>, response: Response<PaginatedDataDto<NewsDto>>) {
                if (response.isSuccessful) {
                    val news = response.body()?.data
                    Log.d("News", "These are the news")
                    val layout = view.findViewById<LinearLayout>(R.id.newsList)
                    createNews(news!![0], layout)
                    createNews(news[1], layout)

                    //click to view more news
                    view.findViewById<TextView>(R.id.newsMore).setOnClickListener {
                        allNewsView = true
                        val allNewsList = view.findViewById<LinearLayout>(R.id.allNewsList)
                        news.forEach {
                            createNews(it, allNewsList)
                        }
                        view.findViewById<ScrollView>(R.id.allNewsLayout).visibility = View.VISIBLE
                        view.findViewById<ScrollView>(R.id.homeView).visibility = View.GONE


                        view.findViewById<Button>(R.id.allNewsExit).setOnClickListener {
                            allNewsView = false
                            view.findViewById<ScrollView>(R.id.allNewsLayout).visibility = View.GONE
                            view.findViewById<ScrollView>(R.id.homeView).visibility = View.VISIBLE
                        }
                    }


                } else {
                    Log.d("News", "Failed to get news")
                }
            }
            override fun onFailure(call: Call<PaginatedDataDto<NewsDto>>, t: Throwable) {
                Log.d("News", t.message.toString())
            }
        })

        val scrollView = view.findViewById<ScrollView>(R.id.scrollViewArticle)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val maxScroll = scrollView.getChildAt(0).height - scrollView.height
            val currentScroll = scrollView.scrollY
            progressBar.progress = (currentScroll.toFloat() / maxScroll.toFloat() * progressBar.max).toInt()
        }
        return view
    }
    fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).roundToInt()
    }
    @SuppressLint("CutPasteId")
    private fun createNews(newsItem: NewsDto, layout: LinearLayout) {
        val item = LinearLayout(context)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 40)
        item.layoutParams = layoutParams

        //create to view image and text
        val webView = WebView(requireContext())

        val webViewParams = LinearLayout.LayoutParams(
            dpToPx(152),dpToPx(106)
        )
        webViewParams.setMargins(0, 0, 40, 0)
        webView.layoutParams = webViewParams

        webView.settings.setJavaScriptEnabled(true)
        val htmlContent =   """
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
        title.textSize = 22f
        title.setTypeface(null, Typeface.BOLD)
        linearInfo.addView(title)

        val description = TextView(context)
        description.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        description.text = newsItem.body
        description.textSize = 18f
        linearInfo.addView(description)
        item.addView(linearInfo)

        item.setOnClickListener{
            val article = view?.findViewById<ConstraintLayout>(R.id.newsLayout)
            view?.findViewById<TextView>(R.id.articleTitle)?.text = newsItem.title
            view?.findViewById<TextView>(R.id.articleCreateBy)?.text = newsItem.createdBy
            view?.findViewById<TextView>(R.id.articleTime)?.text = newsItem.createdAt.toString()
            view?.findViewById<WebView>(R.id.articleImage)?.loadUrl(newsItem.image)
            view?.findViewById<TextView>(R.id.articleContent)?.text = newsItem.body

            article?.visibility = View.VISIBLE
            view?.findViewById<ScrollView>(R.id.allNewsLayout)?.visibility = View.GONE
            view?.findViewById<ScrollView>(R.id.homeView)?.visibility = View.GONE

            view?.findViewById<Button>(R.id.articleExit)?.setOnClickListener{
                //clear the article view
                view?.findViewById<TextView>(R.id.articleTitle)?.text = ""
                view?.findViewById<TextView>(R.id.articleCreateBy)?.text = ""
                view?.findViewById<TextView>(R.id.articleTime)?.text = ""
                view?.findViewById<WebView>(R.id.articleImage)?.loadUrl("about:blank")
                view?.findViewById<TextView>(R.id.articleContent)?.text = ""

                article?.visibility = View.GONE
                if (allNewsView) {
                    view?.findViewById<ScrollView>(R.id.allNewsLayout)?.visibility = View.VISIBLE
                } else {
                    view?.findViewById<ScrollView>(R.id.homeView)?.visibility = View.VISIBLE
                }
            }
        }
        layout.addView(item)
    }
}