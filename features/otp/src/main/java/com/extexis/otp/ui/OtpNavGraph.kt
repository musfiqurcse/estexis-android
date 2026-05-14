package com.extexis.otp.ui

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
import com.extexis.core.navigation.HomeRoute
import com.extexis.core.navigation.LoginRoute
import com.extexis.core.navigation.OtpRoute
import com.extexis.core.presentation.UiMessageEvent
import com.extexis.core.ui.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun NavGraphBuilder.otpNavGraph(navController: NavHostController) {

    composable<OtpRoute> {

        val viewModel = hiltViewModel<OtpViewModel>()
        val state by viewModel.state.collectAsState()

        val hostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    OtpNavigationEvent.Back -> navController.navigateUp()
                    OtpNavigationEvent.ToHome -> {
                        navController.navigate(HomeRoute) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                    OtpNavigationEvent.ToLogin -> {
                        navController.navigate(LoginRoute) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
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

        OtpScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
