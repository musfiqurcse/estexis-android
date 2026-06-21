package com.estexis.faceverification.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.FaceVerificationRoute
import com.estexis.core.navigation.KycRoute
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.faceVerificationNavGraph(navController: NavHostController) {
    composable<FaceVerificationRoute> {
        val viewModel: FaceVerificationViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    FaceVerificationNavigationEvent.Back -> navController.navigateUp()
                    FaceVerificationNavigationEvent.Done -> {
                        navController.getBackStackEntry(KycRoute).savedStateHandle["kyc_needs_refresh"] = true
                        navController.navigateUp()
                    }
                }
            }
        }

        FaceVerificationScreen(
            uiState = uiState,
            event = viewModel::onEvent,
        )
    }
}
