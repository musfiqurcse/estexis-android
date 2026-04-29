package com.extexis.core.android.presentation.splash

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.extexis.core.android.navigation.AppNavState
import com.extexis.core.android.presentation.login.LoginRoute
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable

@Serializable
data object SplashScreenRoute


fun NavGraphBuilder.splashScreenNavGraph(appNavState: AppNavState) {

    composable<SplashScreenRoute> {

        val navController = appNavState.navHostController
        val viewModel = hiltViewModel<SplashViewModel>()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    SplashScreenNavigationEvent.ToLogin -> {
                        navController.navigate(LoginRoute)
                    }
                }
            }
        }

        SplashScreen(
            event = viewModel::onEvent,
        )
    }
}
