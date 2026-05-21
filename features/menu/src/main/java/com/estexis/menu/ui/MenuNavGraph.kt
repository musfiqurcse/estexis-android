package com.estexis.menu.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.estexis.core.navigation.MenuRoute

fun NavGraphBuilder.menuNavGraph() {
    composable<MenuRoute> {
        val viewModel: MenuViewModel = hiltViewModel()
        MenuScreen()
    }
}
