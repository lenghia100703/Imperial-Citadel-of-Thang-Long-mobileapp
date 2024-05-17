package com.example.mobileappui.presentation.exhibition

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobileappui.R
import com.example.mobileappui.dtos.exhibition.ExhibitionDto


class ExhibitionAdapter(private val items: List<ExhibitionDto>, private val onItemClick: (ExhibitionDto) -> Unit) :
    RecyclerView.Adapter<ExhibitionAdapter.ExhibitionViewHolder>() {

    class ExhibitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.image)
        val titleView: TextView = itemView.findViewById(R.id.name)
        val webView: WebView = itemView.findViewById(R.id.image_web_view)

        @SuppressLint("ClickableViewAccessibility", "SetJavaScriptEnabled")
        fun bind(exhibition: ExhibitionDto, onItemClick: (ExhibitionDto) -> Unit) {
            titleView.text = exhibition.name
            Log.d("ExhibitionAdapter", titleView.text as String)
            webView.settings.apply {
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = true
                displayZoomControls = false
            }
            webView.webViewClient = WebViewClient()
            if (exhibition.image.endsWith(".png", true) || exhibition.image.endsWith(".jpg", true) || exhibition.image.endsWith(".jpeg", true)) {
                val htmlData = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <style>
                            body, html {
                                margin: 0;
                                padding: 0;
                                height: 100%;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                            }
                            img {
                                max-width: 100%;
                                max-height: 100%;
                                object-fit: contain;
                            }
                        </style>
                    </head>
                    <body>
                        <img src="${exhibition.image}" alt="Exhibition Image">
                    </body>
                    </html>
                """.trimIndent()
                webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
            } else {
                webView.loadUrl(exhibition.image)
            }

            webView.setOnTouchListener { v, event ->
                itemView.onTouchEvent(event)
            }
            itemView.setOnClickListener { onItemClick(exhibition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExhibitionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_exhibition, parent, false)
        return ExhibitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExhibitionViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClick)
    }

    override fun getItemCount() = items.size
}
