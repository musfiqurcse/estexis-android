package com.extexis.core.android.presentation.otp

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
import com.travelhugai.travelplanner.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class OtpRoute(val email: String, val purpose: String)

fun NavGraphBuilder.otpNavGraph(appNavState: AppNavState) {

    composable<OtpRoute> {

        val navController = appNavState.navHostController
        val viewModel = hiltViewModel<OtpViewModel>()
        val state by viewModel.state.collectAsState()

        val hostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    OtpNavigationEvent.Back -> navController.navigateUp()
                    OtpNavigationEvent.ToHome -> { /* navigate to home */ }
                    OtpNavigationEvent.ToLogin -> navController.navigateUp()
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

        OtpScreen(
            state = state,
            event = viewModel::onEvent,
        )

    }
}
