package com.estexis.home.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.estexis.core.navigation.AddListingRoute
import com.estexis.core.navigation.ListingsRoute
import com.estexis.core.navigation.MenuRoute
import com.estexis.core.navigation.MessagesRoute
import com.estexis.core.navigation.ProfileRoute

enum class HomeTab(val icon: ImageVector, val label: String) {

    LISTINGS(Icons.Default.Home, "Listings"),
    MESSAGES(Icons.Default.ChatBubbleOutline, "Messages"),
    ADD(Icons.Default.Add, "Add"),
    MENU(Icons.Default.BarChart, "Menu"),
    PROFILE(Icons.Default.GridView, "Profile");

    companion object {
        fun fromRoute(route: String?): HomeTab = when (route) {
            ProfileRoute::class.qualifiedName -> PROFILE
            ListingsRoute::class.qualifiedName -> LISTINGS
            AddListingRoute::class.qualifiedName -> ADD
            MessagesRoute::class.qualifiedName -> MESSAGES
            MenuRoute::class.qualifiedName -> MENU
            else -> PROFILE
        }
    }
}
