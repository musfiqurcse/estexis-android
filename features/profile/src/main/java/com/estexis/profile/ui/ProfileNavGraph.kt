package com.estexis.profile.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.KycRoute
import com.estexis.core.navigation.ProfileRoute
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    composable<ProfileRoute> {
        val viewModel: ProfileViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    ProfileNavigationEvent.Back -> navController.navigateUp()
                    ProfileNavigationEvent.ToKyc -> navController.navigate(KycRoute)
                }
            }
        }

        ProfileScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
