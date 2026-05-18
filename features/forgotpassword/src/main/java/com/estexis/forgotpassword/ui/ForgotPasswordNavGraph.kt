package com.estexis.forgotpassword.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.ForgotPasswordRoute
import com.estexis.core.navigation.OtpPurpose
import com.estexis.core.navigation.OtpRoute
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.forgotPasswordNavGraph(navController: NavHostController) {

    composable<ForgotPasswordRoute> {

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
                                lastName = event.lastName,
                                purpose = OtpPurpose.FORGOT_PASSWORD,
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
