package com.example.mobileappui.route

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.post.PostDto
import com.example.mobileappui.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [Search.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewList(i: Int) : Fragment() {
    private lateinit var reviewPosts: List<RelativeLayout>
    private lateinit var buttonLayouts: List<LinearLayout>
    private lateinit var pageButtons: List<Button>
    private var onPage = i
    private val ps = ApiClient.postService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_review_list, container, false)

        reviewPosts = findAllPosts(view)
        buttonLayouts = findLinearLayout(view)
        pageButtons = findButtons(buttonLayouts[0])

        fetchData(onPage)
        for (i in pageButtons.indices) {
            pageButtons[i].setOnClickListener {
                onPage = i
                fetchData(i)
            }
        }
        for (i in reviewPosts.indices) {
            reviewPosts[i].setOnClickListener {
                replaceFragment(ViewReview(onPage * 4 + i))
            }
        }
        return view
    }

    private fun fetchData(page: Int) {
        ps.getAllPost(0).enqueue(object : Callback<PaginatedDataDto<PostDto>> {
            override fun onResponse(call: Call<PaginatedDataDto<PostDto>>, response: Response<PaginatedDataDto<PostDto>>) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        changeData(response, page)
                    }
                }
            }
            override fun onFailure(call: Call<PaginatedDataDto<PostDto>>, t: Throwable) {
            }
        })
    }


    private fun findAllPosts(view: View): List<RelativeLayout> {
        val layouts = mutableListOf<RelativeLayout>()
        if (view is RelativeLayout) {
            layouts.add(view)
        } else if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                layouts.addAll(findAllPosts(child))
            }
        }
        return layouts
    }

    private fun findLinearLayout(view: View): List<LinearLayout> {
        val layouts = mutableListOf<LinearLayout>()
        if (view is LinearLayout) {
            layouts.add(view)
        } else if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                layouts.addAll(findLinearLayout(child))
            }
        }
        return layouts
    }

    private fun findButtons(layout: LinearLayout): List<Button> {
        val buttonList = mutableListOf<Button>()
        for (i in 0 until layout.childCount) {
            val child: View = layout.getChildAt(i)
            if (child is Button) {
                buttonList.add(child)
            }
        }
        return buttonList
    }

    private fun changeData(
        response: Response<PaginatedDataDto<PostDto>>,
        page: Int
    ): Unit {
        val data = response.body()?.data
        var layout : RelativeLayout
        if (data != null) {
            val pages = data.size.div(4).plus(1)
            for (numberOfPage in 0 until pages) {
                val child: View = buttonLayouts[0].getChildAt(numberOfPage)
                if (child is Button) {
                    child.visibility = View.VISIBLE
                }
            }
            val temp = (page * 4 + 4).coerceAtMost(data.size)
            var max = 0
            for (i in  page * 4 ..< temp) {
                val author = data[i].createdBy
                val title = data[i].title
                val imageUrl = data[i].image
                layout = reviewPosts[i%4]
                max = max.coerceAtLeast(i % 4)
                layout.visibility = View.VISIBLE
                for (j in 0 until layout.childCount) {
                    val child: View = layout.getChildAt(j)
                    if (child is TextView) {
                        child.text = "${title} by: ${author} "
                    }
                    if (child is WebView) {
                        child.settings.setJavaScriptEnabled(true)
                        val htmlContent =   """
                                            <html>
                                                <body style="margin:0;padding:0;">
                                            <img src="$imageUrl" alt="Image" style="width:100%;height:100%;"/>
                                                </body>
                                            </html>
                                            """
                        child.loadData(htmlContent, "text/html", "UTF-8")
                    }
                }
            }
            for (i in max + 1..3) {
                reviewPosts[i].visibility = View.GONE
            }
            pageButtons[page].setBackgroundColor(Color.parseColor("#c99c34"))
            for (j in pageButtons.indices) {
                if (j != page) {
                    pageButtons[j].setBackgroundColor(Color.parseColor("#AAA9A9"))
                }
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}