package com.extexis.core.android.presentation.registration

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.extexis.core.android.navigation.AppNavState
import com.extexis.core.android.presentation.common.UiMessageEvent
import com.extexis.core.android.presentation.otp.OtpPurpose
import com.extexis.core.android.presentation.otp.OtpRoute
import com.travelhugai.travelplanner.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object RegistrationRoute

fun NavGraphBuilder.registrationNavGraph(appNavState: AppNavState) {

    composable<RegistrationRoute> {

        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        val hostState = remember { SnackbarHostState() }

        val navController = appNavState.navHostController
        val viewModel = hiltViewModel<RegistrationViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    RegistrationNavigationEvent.Back -> navController.navigateUp()
                    RegistrationNavigationEvent.ToLogin -> navController.navigateUp()
                    is RegistrationNavigationEvent.ToOtpVerification -> {
                        navController.navigate(OtpRoute(email = event.email, purpose = OtpPurpose.Registration.name))
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.uiMessageEvent.collectLatest { uiMessageEvent ->
                when(uiMessageEvent) {
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
            hostState = hostState,
        )
    }
}
