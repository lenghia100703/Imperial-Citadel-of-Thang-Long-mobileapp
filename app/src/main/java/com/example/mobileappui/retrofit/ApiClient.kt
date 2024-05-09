package com.example.mobileappui.retrofit

import com.example.mobileappui.services.auth.AuthService
import com.example.mobileappui.services.banner.BannerService
import com.example.mobileappui.services.exhibition.ExhibitionService
import com.example.mobileappui.services.location.LocationService
import com.example.mobileappui.services.news.NewsService
import com.example.mobileappui.services.paypal.PaypalService
import com.example.mobileappui.services.post.PostService
import com.example.mobileappui.services.question.QuestionService
import com.example.mobileappui.services.ticket.TicketService
import com.example.mobileappui.services.transaction.TransactionService
import com.example.mobileappui.services.user.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val host = "192.168.0.2" // check in terminal run command ipconfig -> host = ipv4 address
    private const val apiURL = "http://${host}:8888/api"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(apiURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsService: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }

    val bannerService: BannerService by lazy {
        retrofit.create(BannerService::class.java)
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val exhibitionService: ExhibitionService by lazy {
        retrofit.create(ExhibitionService::class.java)
    }

    val locationService: LocationService by lazy {
        retrofit.create(LocationService::class.java)
    }

    val paypalService: PaypalService by lazy {
        retrofit.create(PaypalService::class.java)
    }

    val postService: PostService by lazy {
        retrofit.create(PostService::class.java)
    }

    val questionService: QuestionService by lazy {
        retrofit.create(QuestionService::class.java)
    }

    val ticketService: TicketService by lazy {
        retrofit.create(TicketService::class.java)
    }

    val transactionService: TransactionService by lazy {
        retrofit.create(TransactionService::class.java)
    }

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}