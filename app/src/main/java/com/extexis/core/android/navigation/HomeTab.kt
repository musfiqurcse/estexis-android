package com.extexis.core.android.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class HomeTab(val icon: ImageVector, val label: String) {
    DASHBOARD(Icons.Default.GridView, "Dashboard"),
    LISTINGS(Icons.Default.Home, "Listings"),
    ADD(Icons.Default.Add, "Add"),
    MESSAGES(Icons.Default.ChatBubbleOutline, "Messages"),
    MENU(Icons.Default.BarChart, "Menu"),
}
