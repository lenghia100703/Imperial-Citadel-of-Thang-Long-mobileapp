package com.example.mobileappui.presentation.home.imgSlider

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.mobileappui.R
import com.example.mobileappui.dtos.banner.BannerDto
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.news.NewsDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.banner.BannerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class ImageSlider: Fragment() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var indicatorContainer : LinearLayout
    private lateinit var hander : Handler
    private lateinit var imageList: ArrayList<String>
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.slide_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setUpTransformer()

        indicatorContainer = view.findViewById(R.id.indicatorContainer)
        for (i in imageList.indices) {
            val imageView = ImageView(context)
            imageView.setImageResource(if (i == 0) R.drawable.indicator_active else R.drawable.indicator_inactive)
            indicatorContainer.addView(imageView)
        }
        //
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                for (i in 0 until indicatorContainer.childCount) {
                    val imageView = indicatorContainer.getChildAt(i) as ImageView
                    val index = if (position >= indicatorContainer.childCount) position % indicatorContainer.childCount else position
                    imageView.setImageResource(if (i == index) R.drawable.indicator_active else R.drawable.indicator_inactive)
                }
                hander.removeCallbacks(runnable)
                hander.postDelayed(runnable, 3000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        hander.removeCallbacks(runnable)
    }
    override fun onResume() {
        super.onResume()
        hander.postDelayed(runnable, 2000)
    }
    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager2.setPageTransformer(transformer)
    }
    private fun init() {
        viewPager2 = requireView().findViewById(R.id.viewPager2)
        hander = Handler(Looper.myLooper()!!)
        imageList = ArrayList()
        val bannerService: BannerService = ApiClient.bannerService
        bannerService.getALlBannerIsActive().enqueue(object : Callback<List<BannerDto>> {
            override fun onResponse(call: Call<List<BannerDto>>, response: Response<List<BannerDto>>) {
                if (response.isSuccessful) {
                    val banners: List<BannerDto> = response.body()!!
                    for (banner in banners) {
                        imageList.add(banner.image)
                    }

                } else {
                    Log.d("News", "Failed to get news")
                }
            }
            override fun onFailure(call: Call<List<BannerDto>>, t: Throwable) {
                Log.d("News", t.message.toString())
            }
        })
        adapter = ImageAdapter(imageList, viewPager2)
        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
}