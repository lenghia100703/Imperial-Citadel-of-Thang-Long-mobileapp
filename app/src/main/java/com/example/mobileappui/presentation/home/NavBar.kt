package com.example.mobileappui.presentation.home

import android.os.Bundle
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
import androidx.compose.runtime.Composable
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
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.items.dropletbutton.DropletButton


class NavBar : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DropletButtonNavBar()
            }
        }
    }
}
@Composable
fun DropletButtonNavBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val darkYellow = Color(0xFF786b2e)
    AnimatedNavigationBar(
        modifier = Modifier
            .padding(top = 50.dp)
            .height(85.dp),
        selectedIndex = selectedItem,
        ballColor = darkYellow,

        ballAnimation = Parabolic(tween(Duration, easing = LinearOutSlowInEasing)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
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
                onClick = { selectedItem = index
                          when(selectedItem) {
                              0 -> println("Person")
                              1 -> println("Search")
                              2 -> println("Home")
                              3 -> println("Book ticket")
                              4 -> println("Menu")
                            }
                          },
                icon = it.icon,
                dropletColor = Color.White,
                animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
            )
        }
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