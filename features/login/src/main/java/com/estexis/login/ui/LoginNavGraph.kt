package com.estexis.login.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.ExistingUserVerificationRoute
import com.estexis.core.navigation.ForgotPasswordRoute
import com.estexis.core.navigation.HomeRoute
import com.estexis.core.navigation.LoginRoute
import com.estexis.core.navigation.RegistrationRoute
import com.estexis.core.presentation.UiMessageEvent
import com.estexis.core.ui.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun NavGraphBuilder.loginNavGraph(navController: NavHostController) {

    composable<LoginRoute> {

        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val hostState = remember { SnackbarHostState() }
        val viewModel: LoginViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    LoginNavigationEvent.ToHome -> {
                        navController.navigate(HomeRoute) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                    LoginNavigationEvent.ToForgotPassword -> navController.navigate(ForgotPasswordRoute)
                    LoginNavigationEvent.ToSignUp -> navController.navigate(RegistrationRoute)
                    is LoginNavigationEvent.VerifyEmail -> {
                        navController.navigate(
                            ExistingUserVerificationRoute(
                                event.email
                            )
                        )
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.uiMessageEvent.collectLatest { uiMessageEvent ->
                when (uiMessageEvent) {
                    is UiMessageEvent.SnackBarMessage -> {
                        uiMessageEvent.errorMessage?.let { uiText ->
                            scope.launch {
                                hostState.showSnackbar(uiText.asString(context))
                            }
                        }
                    }
                    is UiMessageEvent.ToastMessage -> {
                        uiMessageEvent.errorMessage?.let { uiText ->
                            context.showToast(uiText.asString(context))
                        }
                    }
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
