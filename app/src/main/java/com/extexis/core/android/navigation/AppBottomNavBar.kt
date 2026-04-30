package com.extexis.core.android.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.android.ui.theme.AppTheme
import com.extexis.core.android.ui.theme.ExtexisAndroidTheme

enum class HomeTab(val icon: ImageVector, val label: String) {
    Dashboard(Icons.Default.GridView, "Dashboard"),
    Listings(Icons.Default.Home, "Listings"),
    Add(Icons.Default.Add, "Add"),
    Messages(Icons.Default.ChatBubbleOutline, "Messages"),
    Menu(Icons.Default.BarChart, "Menu"),
}

private val NavItemColor = Color(0xFF8A8A8A)
private val NavBarContainerColor = Color(0xFF4D4D4F)

@Composable
fun AppBottomNavBar(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.onPrimary)
            .navigationBarsPadding()
            .padding(horizontal = dimensions.spaces.x2, vertical = dimensions.spaces.x2),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(dimensions.radius.pill))
                .background(NavBarContainerColor)
                .height(AppTheme.dimensions.sizes.x18)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomeTab.entries.forEach { tab ->

                val isLast = tab == HomeTab.entries.last()

                val circleColor = if (tab == HomeTab.Add) colors.primary else colors.onTertiary

                Box(
                    modifier = Modifier
                        .padding(end =
                            if(isLast) AppTheme.dimensions.spaces.x0
                            else AppTheme.dimensions.spaces.x3
                        )
                        .size(dimensions.sizes.x13)
                        .clip(CircleShape)
                        .background(circleColor),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(onClick = { onTabSelected(tab) }) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = if (tab == selectedTab) colors.success else colors.white,
                            modifier = Modifier.size(dimensions.sizes.x7),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppBottomNavBarPreview() {
    ExtexisAndroidTheme {
        var selectedTab by remember { mutableStateOf(HomeTab.Dashboard) }
        AppBottomNavBar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it },
        )
    }
}
