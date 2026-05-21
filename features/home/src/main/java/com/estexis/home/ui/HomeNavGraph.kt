package com.estexis.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.estexis.addlisting.ui.addListingNavGraph
import com.estexis.core.navigation.AddListingRoute
import com.estexis.core.navigation.DashboardRoute
import com.estexis.core.navigation.HomeRoute
import com.estexis.core.navigation.ListingsRoute
import com.estexis.core.navigation.MenuRoute
import com.estexis.core.navigation.MessagesRoute
import com.estexis.core.navigation.ProfileRoute
import com.estexis.core.ui.theme.AppTheme
import com.estexis.dashboard.ui.dashboardNavGraph
import com.estexis.listings.ui.listingsNavGraph
import com.estexis.menu.ui.menuNavGraph
import com.estexis.messages.ui.messagesNavGraph

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable<HomeRoute> {
        HomeShellScreen(
            onProfileClicked = { navController.navigate(ProfileRoute) },
        )
    }
}

@Composable
private fun HomeShellScreen(
    onProfileClicked: () -> Unit,
) {
    val homeNavController = rememberNavController()
    val backStackEntry by homeNavController.currentBackStackEntryAsState()
    val selectedTab = HomeTab.fromRoute(backStackEntry?.destination?.route)

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HomeTopBar(onProfileClicked = onProfileClicked)
        },
        bottomBar = {
            AppBottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = { tab -> navigateToTab(homeNavController, tab) },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            NavHost(
                navController = homeNavController,
                startDestination = DashboardRoute,
            ) {
                dashboardNavGraph()
                listingsNavGraph()
                addListingNavGraph()
                messagesNavGraph()
                menuNavGraph()
            }
        }
    }
}

@Composable
private fun HomeTopBar(onProfileClicked: () -> Unit) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.onPrimary)
            .statusBarsPadding()
            .padding(horizontal = dimensions.spaces.x4, vertical = dimensions.spaces.x3),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(dimensions.sizes.x10)
                .clip(CircleShape)
                .background(colors.surface)
                .clickable(onClick = onProfileClicked),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = colors.tertiary,
                modifier = Modifier.size(dimensions.sizes.x6),
            )
        }
    }
}

private fun navigateToTab(navController: NavHostController, tab: HomeTab) {
    val route: Any = when (tab) {
        HomeTab.DASHBOARD -> DashboardRoute
        HomeTab.LISTINGS -> ListingsRoute
        HomeTab.ADD -> AddListingRoute
        HomeTab.MESSAGES -> MessagesRoute
        HomeTab.MENU -> MenuRoute
    }
    navController.navigate(route) {
        popUpTo(DashboardRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
