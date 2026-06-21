package com.estexis.faceverification.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppTitleBar
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.util.addBackground
import com.estexis.faceverification.R

@Composable
fun FaceVerificationScreen(
    uiState: FaceVerificationUiState,
    event: (FaceVerificationUiEvent) -> Unit,
) {
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addBackground(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            VerticalSpacer(dimensions.spaces.x2)

            AppTitleBar(
                onBackClick = { event(FaceVerificationUiEvent.BackClicked) },
                title = stringResource(R.string.face_verification_title),
            )

            VerticalSpacer(dimensions.spaces.x6)

            Column(modifier = Modifier.padding(horizontal = dimensions.spaces.x4)) {
                FaceVerificationStep(
                    uiState = uiState,
                    event = event,
                )
            }

            VerticalSpacer(dimensions.spaces.x6)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.spaces.x4, vertical = dimensions.spaces.x4),
        ) {
            AppButton(
                text = stringResource(R.string.face_verification_cta_submit),
                onClick = { event(FaceVerificationUiEvent.DoneClicked) },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.isCompleted && !uiState.isLoading,
                isLoading = uiState.isLoading,
            )
        }
    }
}
