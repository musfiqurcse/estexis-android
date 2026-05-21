package com.estexis.listings.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.estexis.core.navigation.ListingsRoute

fun NavGraphBuilder.listingsNavGraph() {
    composable<ListingsRoute> {
        val viewModel: ListingsViewModel = hiltViewModel()
        ListingsScreen()
    }
}
