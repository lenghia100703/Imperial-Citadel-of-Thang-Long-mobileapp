package com.example.mobileappui.presentation.home.imgSlider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mobileappui.R

class ImageAdapter(private val imgList : ArrayList<String>, private val viewPager2 : ViewPager2) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.img_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(imgList[position])
            .into(holder.imageView)
        //khi đến ảnh cuối cùng thì thêm ảnh vào
        if (position == imgList.size - 1) {
            viewPager2.post(runnable)
        }
    }
    //thêm list ảnh vào
    private val runnable = Runnable {
        imgList.addAll(imgList)
        notifyDataSetChanged()
    }
}