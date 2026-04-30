package com.extexis.core.android.presentation.forgotpassword

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.extexis.core.android.navigation.AppNavState
import com.extexis.core.android.presentation.otp.OtpPurpose
import com.extexis.core.android.presentation.otp.OtpRoute
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.Serializable

@Serializable
data object ForgotPasswordRoute

fun NavGraphBuilder.forgotPasswordNavGraph(appNavState: AppNavState) {

    composable<ForgotPasswordRoute> {

        val navController = appNavState.navHostController
        val viewModel = hiltViewModel<ForgotPasswordViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    ForgotPasswordNavigationEvent.Back -> navController.navigateUp()
                    is ForgotPasswordNavigationEvent.ToOtp -> {
                        navController.navigate(
                            OtpRoute(
                                email = event.email,
                                purpose = OtpPurpose.ForgotPassword.name,
                            )
                        )
                    }
                }
            }
        }

        ForgotPasswordScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
