package com.estexis.addlisting.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.estexis.core.navigation.AddListingRoute

fun NavGraphBuilder.addListingNavGraph() {
    composable<AddListingRoute> {
        val viewModel: AddListingViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        AddListingScreen(
            uiState = uiState,
            formState = viewModel.formState,
            event = viewModel::onEvent,
        )
    }
}
