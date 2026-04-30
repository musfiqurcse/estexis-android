package com.extexis.core.android.presentation.otp

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.extexis.core.android.navigation.AppNavState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable

@Serializable
data class OtpRoute(val email: String, val purpose: String)

fun NavGraphBuilder.otpNavGraph(appNavState: AppNavState) {

    composable<OtpRoute> {

        val navController = appNavState.navHostController
        val viewModel = hiltViewModel<OtpViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    OtpNavigationEvent.Back -> navController.navigateUp()
                    OtpNavigationEvent.ToHome -> { /* navigate to home */ }
                    OtpNavigationEvent.ToLogin -> navController.navigateUp()
                }
            }
        }

        OtpScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
