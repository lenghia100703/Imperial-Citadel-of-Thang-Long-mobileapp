package com.example.mobileappui.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.location.LocationDto
import com.example.mobileappui.dtos.post.PostDto
import com.example.mobileappui.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewLocation() : Fragment() {
    // TODO: Rename and change types of parameters
    private val ls = ApiClient.locationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_location, container, false)
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
        ls.getAllLocation(0).enqueue(object : Callback<PaginatedDataDto<LocationDto>> {
            override fun onResponse(call: Call<PaginatedDataDto<LocationDto>>, response: Response<PaginatedDataDto<LocationDto>>) {
                if (isAdded) {
                    if (response.isSuccessful) {
                        changeData(response, page)
                    }
                }
            }
            override fun onFailure(call: Call<PaginatedDataDto<LocationDto>>, t: Throwable) {
            }
        })
    }

    private fun changeData(
        response: Response<PaginatedDataDto<LocationDto>>,
        page: Int
    ): Unit {
        val data = response.body()?.data
        var layout : LinearLayout
        if (data != null) {

        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}