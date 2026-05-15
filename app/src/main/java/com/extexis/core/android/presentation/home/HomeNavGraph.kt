package com.extexis.core.android.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.extexis.core.android.navigation.AppBottomNavBar
import com.extexis.core.android.navigation.HomeTab
import com.extexis.core.navigation.HomeRoute

fun NavGraphBuilder.homeNavGraph() {
    composable<HomeRoute> {
        HomeScreen()
    }
}

@Composable
private fun HomeScreen() {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.DASHBOARD) }

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            AppBottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (selectedTab) {
                HomeTab.DASHBOARD -> DashboardScreen()
                HomeTab.LISTINGS -> ListingsScreen()
                HomeTab.ADD -> AddListingScreen()
                HomeTab.MESSAGES -> MessagesScreen()
                HomeTab.MENU -> MenuScreen()
            }
        }
    }
}
