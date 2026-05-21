package com.estexis.dashboard.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.estexis.core.navigation.DashboardRoute

fun NavGraphBuilder.dashboardNavGraph() {
    composable<DashboardRoute> {
        val viewModel: DashboardViewModel = hiltViewModel()
        DashboardScreen()
    }
}
