package com.extexis.registration.ui

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
import com.extexis.core.navigation.OtpPurpose
import com.extexis.core.navigation.OtpRoute
import com.extexis.core.navigation.RegistrationRoute
import com.extexis.core.presentation.UiMessageEvent
import com.extexis.core.ui.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun NavGraphBuilder.registrationNavGraph(navController: NavHostController) {

    composable<RegistrationRoute> {

        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val hostState = remember { SnackbarHostState() }

        val viewModel = hiltViewModel<RegistrationViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    RegistrationNavigationEvent.Back -> navController.navigateUp()
                    RegistrationNavigationEvent.ToLogin -> navController.navigateUp()
                    is RegistrationNavigationEvent.ToOtpVerification -> {
                        navController.navigate(
                            OtpRoute(
                                email = event.email,
                                purpose = OtpPurpose.REGISTRATION,
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

        RegistrationScreen(
            state = state,
            formState = viewModel.formState,
            event = viewModel::onEvent,
            // hostState = hostState,
        )
    }
}
