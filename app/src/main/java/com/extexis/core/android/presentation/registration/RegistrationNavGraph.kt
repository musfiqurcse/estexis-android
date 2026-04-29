package com.extexis.core.android.presentation.registration

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
                    RegistrationNavigationEvent.ToHome -> { /* navigate to home */ }
                }
            }
        }

        RegistrationScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
