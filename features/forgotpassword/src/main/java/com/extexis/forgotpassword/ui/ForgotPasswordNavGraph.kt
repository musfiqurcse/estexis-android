package com.extexis.forgotpassword.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.extexis.core.navigation.ForgotPasswordRoute
import com.extexis.core.navigation.OtpPurpose
import com.extexis.core.navigation.OtpRoute
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
                                purpose = OtpPurpose.VERIFY_EXISTING_USER,
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
