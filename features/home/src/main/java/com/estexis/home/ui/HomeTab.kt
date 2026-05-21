package com.estexis.home.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.estexis.core.navigation.AddListingRoute
import com.estexis.core.navigation.DashboardRoute
import com.estexis.core.navigation.ListingsRoute
import com.estexis.core.navigation.MenuRoute
import com.estexis.core.navigation.MessagesRoute

enum class HomeTab(val icon: ImageVector, val label: String) {
    DASHBOARD(Icons.Default.GridView, "Dashboard"),
    LISTINGS(Icons.Default.Home, "Listings"),
    ADD(Icons.Default.Add, "Add"),
    MESSAGES(Icons.Default.ChatBubbleOutline, "Messages"),
    MENU(Icons.Default.BarChart, "Menu");

    companion object {
        fun fromRoute(route: String?): HomeTab = when (route) {
            DashboardRoute::class.qualifiedName -> DASHBOARD
            ListingsRoute::class.qualifiedName -> LISTINGS
            AddListingRoute::class.qualifiedName -> ADD
            MessagesRoute::class.qualifiedName -> MESSAGES
            MenuRoute::class.qualifiedName -> MENU
            else -> DASHBOARD
        }
    }
}
