package com.estexis.kyc.passport.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.PassportVerificationRoute
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.passportVerificationNavGraph(navController: NavHostController) {
    composable<PassportVerificationRoute> {
        val viewModel: PassportVerificationViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    PassportVerificationNavigationEvent.Back -> navController.navigateUp()
                    PassportVerificationNavigationEvent.Submitted -> navController.navigateUp()
                }
            }
        }

        PassportVerificationScreen(
            uiState = uiState,
            formState = viewModel.formState,
            event = viewModel::onEvent,
        )
    }
}
