package com.estexis.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.estexis.addlisting.ui.addListingNavGraph
import com.estexis.core.navigation.AddListingRoute
import com.estexis.core.navigation.HomeRoute
import com.estexis.core.navigation.ListingsRoute
import com.estexis.core.navigation.MenuRoute
import com.estexis.core.navigation.MessagesRoute
import com.estexis.core.navigation.ProfileRoute
import com.estexis.kyc.ui.kycNavGraph
import com.estexis.listings.ui.listingsNavGraph
import com.estexis.menu.ui.menuNavGraph
import com.estexis.messages.ui.messagesNavGraph
import com.estexis.profile.ui.profileNavGraph

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    composable<HomeRoute> {
        HomeShellScreen(outerNavController = navController)
    }
}

@Composable
fun HomeShellScreen(outerNavController: NavHostController) {
    val homeNavController = rememberNavController()
    val backStackEntry by homeNavController.currentBackStackEntryAsState()
    val selectedTab = HomeTab.fromRoute(backStackEntry?.destination?.route)

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0),
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
                startDestination = ListingsRoute,
            ) {
                listingsNavGraph()
                menuNavGraph()
                addListingNavGraph()
                messagesNavGraph()
                profileNavGraph(outerNavController)

            }
        }
    }
}

private fun navigateToTab(navController: NavHostController, tab: HomeTab) {
    val route: Any = when (tab) {
        HomeTab.PROFILE -> ProfileRoute
        HomeTab.LISTINGS -> ListingsRoute
        HomeTab.ADD -> AddListingRoute
        HomeTab.MESSAGES -> MessagesRoute
        HomeTab.MENU -> MenuRoute
    }
    navController.navigate(route) {
        popUpTo(ListingsRoute) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
