package com.estexis.verifyexistinguser.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.ExistingUserVerificationRoute
import com.estexis.core.navigation.OtpPurpose
import com.estexis.core.navigation.OtpRoute
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.existingUserVerificationNavGraph(navController: NavHostController) {

    composable<ExistingUserVerificationRoute> {

        val viewModel = hiltViewModel<ExistingUserVerificationViewModel>()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    ExistingUserVerificationNavigationEvent.Back -> navController.navigateUp()
                    is ExistingUserVerificationNavigationEvent.ToOtp -> {
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

        ExistingUserVerificationScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
