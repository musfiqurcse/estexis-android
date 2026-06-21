package com.estexis.twofactorauth.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.TwoFactorAuthRoute
import com.estexis.core.presentation.UiMessageEvent
import com.estexis.core.ui.util.showToast
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.twoFactorAuthNavGraph(navController: NavHostController) {
    composable<TwoFactorAuthRoute> {
        val viewModel = hiltViewModel<TwoFactorAuthViewModel>()
        val state by viewModel.state.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    TwoFactorAuthNavigationEvent.Back -> navController.navigateUp()
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.uiMessageEvent.collectLatest { uiMessageEvent ->
                when (uiMessageEvent) {
                    is UiMessageEvent.ToastMessage ->
                        uiMessageEvent.errorMessage?.let { context.showToast(it.asString(context)) }
                    is UiMessageEvent.SnackBarMessage -> Unit
                }
            }
        }

        TwoFactorAuthScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
