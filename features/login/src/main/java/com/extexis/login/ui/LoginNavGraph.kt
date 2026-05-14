package com.extexis.login.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.extexis.core.navigation.ForgotPasswordRoute
import com.extexis.core.navigation.HomeRoute
import com.extexis.core.navigation.LoginRoute
import com.extexis.core.navigation.RegistrationRoute
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.loginNavGraph(navController: NavHostController) {

    composable<LoginRoute> {

        val viewModel: LoginViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    LoginNavigationEvent.Back -> navController.navigateUp()
                    LoginNavigationEvent.ToHome -> {
                        navController.navigate(HomeRoute) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                    LoginNavigationEvent.ToForgotPassword -> navController.navigate(ForgotPasswordRoute)
                    LoginNavigationEvent.ToSignUp -> navController.navigate(RegistrationRoute)
                    is LoginNavigationEvent.VerifyEmail -> {}
                }
            }
        }

        LoginScreen(
            state = state,
            formState = viewModel.formState,
            event = viewModel::onEvent,
        )
    }
}
