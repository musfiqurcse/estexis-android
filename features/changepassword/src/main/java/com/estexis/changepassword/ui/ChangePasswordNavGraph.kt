package com.estexis.changepassword.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.ChangePasswordRoute
import com.estexis.core.presentation.UiMessageEvent
import com.estexis.core.ui.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun NavGraphBuilder.changePasswordNavGraph(navController: NavHostController) {
    composable<ChangePasswordRoute> {
        val viewModel = hiltViewModel<ChangePasswordViewModel>()
        val state by viewModel.state.collectAsState()
        val scope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    ChangePasswordNavigationEvent.Back -> navController.navigateUp()
                    ChangePasswordNavigationEvent.Success -> navController.navigateUp()
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.uiMessageEvent.collectLatest { uiMessageEvent ->
                when (uiMessageEvent) {
                    is UiMessageEvent.ToastMessage ->
                        uiMessageEvent.errorMessage?.let { context.showToast(it.asString(context)) }
                    is UiMessageEvent.SnackBarMessage -> {
                        uiMessageEvent.errorMessage?.let { uiText ->
                            scope.launch { /* snackbar if needed */ }
                        }
                    }
                }
            }
        }

        ChangePasswordScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }
}
