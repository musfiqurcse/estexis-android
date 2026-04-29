package com.extexis.core.android.presentation.login

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.extexis.core.android.navigation.AppNavState
import com.extexis.core.android.presentation.registration.RegistrationRoute
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavGraphBuilder.loginNavGraph(appNavState: AppNavState) {

    composable<LoginRoute> {

        val navController = appNavState.navHostController
        val viewModel = hiltViewModel<LoginViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    LoginNavigationEvent.Back -> navController.navigateUp()
                    LoginNavigationEvent.ToHome -> { /* navigate to home */ }
                    LoginNavigationEvent.ToForgotPassword -> { /* navigate to forgot password */ }
                    LoginNavigationEvent.ToSignUp -> { navController.navigate(RegistrationRoute) }
                }
            }
        }

        LoginScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
