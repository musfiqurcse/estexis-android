package com.estexis.core.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppNavState(
    navHostController: NavHostController = rememberNavController(),
) = remember(navHostController) {
    AppNavState(navHostController)
}

@Stable
class AppNavState(
    val navHostController: NavHostController,
)
