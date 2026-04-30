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
import com.extexis.core.android.navigation.AppNavState
import com.extexis.core.android.navigation.HomeTab
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeNavGraph(appNavState: AppNavState) {
    composable<HomeRoute> {
        HomeScreen()
    }
}

@Composable
private fun HomeScreen() {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.Dashboard) }

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
                HomeTab.Dashboard -> DashboardScreen()
                HomeTab.Listings -> ListingsScreen()
                HomeTab.Add -> AddListingScreen()
                HomeTab.Messages -> MessagesScreen()
                HomeTab.Menu -> MenuScreen()
            }
        }
    }
}
