package com.extexis.splash.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.extexis.core.navigation.LoginRoute
import com.extexis.core.navigation.SplashScreenRoute

fun NavGraphBuilder.splashScreenNavGraph(navController: NavHostController) {
    composable<SplashScreenRoute> {
        SplashScreen(
            event = { event ->
                when (event) {
                    SplashScreenUiEvent.GetStarted -> navController.navigate(LoginRoute)
                }
            },
        )
    }
}
