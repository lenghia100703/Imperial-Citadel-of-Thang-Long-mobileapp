package com.example.mobileappui.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.mobileappui.R
import com.example.mobileappui.colorButtons.BellColorButton
import com.example.mobileappui.colorButtons.ButtonBackground
import com.example.mobileappui.databinding.ActivityMainBinding
import com.example.mobileappui.route.Account
import com.example.mobileappui.route.BookTicket
import com.example.mobileappui.route.Home
import com.example.mobileappui.route.Search
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.items.dropletbutton.DropletButton

class NavBar : Fragment() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        replaceFragment(Home())
        return ComposeView(requireContext()).apply {
            setContent {

                binding = ActivityMainBinding.inflate(layoutInflater)

                var selectedItem by remember { mutableIntStateOf(2) }
                val darkYellow = Color(0xFF786b2e)
                AnimatedNavigationBar(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .height(58.dp),
                    selectedIndex = selectedItem,
                    ballColor = Color.Transparent,
                    ballAnimation = Straight(tween(Duration, easing = LinearOutSlowInEasing)),
                    indentAnimation = Height(
                        indentWidth = 0.dp,
                        indentHeight = 0.dp,
                        animationSpec = tween(
                            DoubleDuration,
                            easing = { OvershootInterpolator().getInterpolation(it) })
                    )
                ) {
                    dropletButtons.forEachIndexed { index, it ->
                        DropletButton(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(darkYellow),
                            isSelected = selectedItem == index,
                            onClick = {
                                // Điều kiện kiểm tra nếu nút không phải là Menu
                                if (it.description != "Menu") {
                                    selectedItem = index
                                }

                                // chuyển hướng
                                when (it.description) {
                                    "Person" -> replaceFragment(Account())
                                    "Search" -> replaceFragment(Search())
                                    "Home" -> replaceFragment(Home())
                                    "Book ticket" -> replaceFragment(BookTicket())
                                    "Menu" -> {
                                        // Logic xử lý cho Menu, nếu cần
                                    }

                                    else -> println("Nothing")
                                }

                                // Gửi dữ liệu thông qua Intent
                                val sendData = Intent(activity, MainScreen::class.java).apply {
                                    putExtra("openMenu", it.description == "Menu")
                                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                                }
                                Log.d("NavBar", "Putting 'openMenu' into Intent")
                                startActivity(sendData)
                            },
                            icon = it.icon,
                            dropletColor = Color.White,
                            animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
                        )
                    }
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

@Stable
data class Item(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    val description: String,
    val animationType: BellColorButton = BellColorButton(
        tween(500),
        background = ButtonBackground(R.drawable.plus)
    ),
)

val dropletButtons = listOf(
    Item(
        icon = R.drawable.person,
        isSelected = false,
        description = "Person"
    ),
    Item(
        icon = R.drawable.search,
        isSelected = false,
        description = "Search"
    ),
    Item(
        icon = R.drawable.home,
        isSelected = false,
        description = "Home"
    ),
    Item(
        icon = R.drawable.ticket,
        isSelected = false,
        description = "Book ticket"
    ),
    Item(
        icon = R.drawable.menu,
        isSelected = false,
        description = "Menu"
    ),
)

const val Duration = 500
const val DoubleDuration = 1000
