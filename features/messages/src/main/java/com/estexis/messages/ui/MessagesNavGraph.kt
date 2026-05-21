package com.estexis.messages.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.estexis.core.navigation.MessagesRoute

fun NavGraphBuilder.messagesNavGraph() {
    composable<MessagesRoute> {
        val viewModel: MessagesViewModel = hiltViewModel()
        MessagesScreen()
    }
}
