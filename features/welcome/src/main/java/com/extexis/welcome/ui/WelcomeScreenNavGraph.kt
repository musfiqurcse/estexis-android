package com.extexis.welcome.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.extexis.core.navigation.LoginRoute
import com.extexis.core.navigation.SplashScreenRoute
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.welcomeScreenNavGraph(navController: NavHostController) {
    composable<SplashScreenRoute> {

        val viewModel: WelcomeViewModel = hiltViewModel()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    WelcomeScreenNavigationEvent.ToLogin -> {
                        navController.navigate(LoginRoute) {
                            popUpTo(SplashScreenRoute) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            }
        }

        WelcomeScreen(
            event = viewModel::onEvent,
        )
    }
}
