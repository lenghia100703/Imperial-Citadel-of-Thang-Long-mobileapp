package com.example.mobileappui.presentation.home.imgSlider

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import kotlin.math.abs

class ImageSlider: Fragment() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var indicatorContainer : LinearLayout
    private lateinit var hander : Handler
    private lateinit var imageList: ArrayList<Int>
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
        imageList.add(R.drawable.doanmon)
        imageList.add(R.drawable.doco)
        imageList.add(R.drawable.hoangthanh)
        adapter = ImageAdapter(imageList, viewPager2)
        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
}