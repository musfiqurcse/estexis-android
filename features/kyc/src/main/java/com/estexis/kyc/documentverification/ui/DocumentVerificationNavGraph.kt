package com.estexis.kyc.documentverification.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.DocumentVerificationRoute
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.documentVerificationNavGraph(navController: NavHostController) {
    composable<DocumentVerificationRoute> {
        val viewModel: DocumentVerificationViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    PassportVerificationNavigationEvent.Back -> navController.navigateUp()
                    PassportVerificationNavigationEvent.Submitted -> navController.navigateUp()
                }
            }
        }

        DocumentVerificationScreen(
            uiState = uiState,
            formState = viewModel.formState,
            event = viewModel::onEvent,
        )
    }
}
