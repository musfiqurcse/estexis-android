package com.estexis.kyc.document.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.DocumentUploadRoute
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.documentUploadNavGraph(navController: NavHostController) {
    composable<DocumentUploadRoute> {
        val viewModel: DocumentUploadViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    DocumentUploadNavigationEvent.Back -> navController.navigateUp()
                    DocumentUploadNavigationEvent.Submitted -> navController.navigateUp()
                }
            }
        }

        DocumentUploadScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
