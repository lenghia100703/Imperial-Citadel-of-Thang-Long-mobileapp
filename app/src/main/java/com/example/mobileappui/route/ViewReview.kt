package com.example.mobileappui.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.post.PostDto
import com.example.mobileappui.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewReview(onPage: Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private val ps = ApiClient.postService
    private lateinit var postLayouts: List<LinearLayout>
    private lateinit var returnButton: Button
    private val onPage = onPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_review, container, false)
        postLayouts = findLinearLayout(view)
        returnButton = postLayouts[0].getChildAt(0) as Button
        fetchData(onPage)
        returnButton.setOnClickListener {
            replaceFragment(ReviewList(onPage / 4))
        }
        return view
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

    private fun changeData(
        response: Response<PaginatedDataDto<PostDto>>,
        page: Int
    ): Unit {
        val data = response.body()?.data
        var layout : LinearLayout
        if (data != null) {
                val author = data[onPage].createdBy
                val title = data[onPage].title
                val imageUrl = data[onPage].image
                val rating = title.length % 6;
                layout = postLayouts[0]
                for (j in 0 until layout.childCount) {
                    val child: View = layout.getChildAt(j)
                    if (child is TextView) {
                        if (child.id == R.id.title){
                            child.text = "${title} "
                        }
                        else if (child.id == R.id.author) {
                            child.text = " By: ${author}"
                        }
                    }
                    if (child is RatingBar) {
                        child.rating = rating.toFloat()
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
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}