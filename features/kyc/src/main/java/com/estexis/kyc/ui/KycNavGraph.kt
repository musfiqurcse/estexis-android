package com.estexis.kyc.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.estexis.core.navigation.DocumentUploadRoute
import com.estexis.core.navigation.KycRoute
import com.estexis.core.navigation.PassportVerificationRoute
import com.estexis.kyc.document.ui.documentUploadNavGraph
import com.estexis.kyc.passport.ui.passportVerificationNavGraph
import kotlinx.coroutines.flow.collectLatest

fun NavGraphBuilder.kycNavGraph(navController: NavHostController) {
    composable<KycRoute> {
        val viewModel: KycViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.navigationEvent.collectLatest { event ->
                when (event) {
                    KycNavigationEvent.Back -> navController.navigateUp()
                    KycNavigationEvent.ToPassportVerification ->
                        navController.navigate(PassportVerificationRoute)
                    is KycNavigationEvent.ToDocumentUpload ->
                        navController.navigate(DocumentUploadRoute(event.type))
                }
            }
        }

        KycScreen(
            state = state,
            event = viewModel::onEvent,
        )
    }

    passportVerificationNavGraph(navController)
    documentUploadNavGraph(navController)
}
