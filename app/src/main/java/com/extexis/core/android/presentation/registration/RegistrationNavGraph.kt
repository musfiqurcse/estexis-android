package com.extexis.core.android.presentation.registration

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
data object RegistrationRoute

fun NavGraphBuilder.registrationNavGraph(appNavState: AppNavState) {

    composable<RegistrationRoute> {

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

        RegistrationScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
