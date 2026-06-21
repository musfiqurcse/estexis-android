package com.estexis.kyc.documentverification.ui.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.gds.AppIcon
import com.estexis.core.ui.gds.DocumentCaptureCard
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.kyc.R
import com.estexis.kyc.documentverification.ui.DocumentPhotoTarget
import com.estexis.kyc.documentverification.ui.DocumentVerificationFormState
import com.estexis.kyc.documentverification.ui.DocumentVerificationUiState
import com.estexis.kyc.documentverification.ui.DocumentVerificationUiEvent

@Composable
fun DocumentSubmissionStep(
    uiState: DocumentVerificationUiState,
    formState: DocumentVerificationFormState,
    event: (DocumentVerificationUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.passport_verification_take_clear_photo),
            style = AppTextStyles.BodyText2Regular,
            color = colors.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        VerticalSpacer(dimensions.spaces.x6)

        DocumentCaptureCard(
            label = stringResource(uiState.documentFrontPageLabel),
            capturedImageUri = formState.coverPhotoUri,
            placeholderImage = AppIcon.CoverPage.resId,
            onClick = { event(DocumentVerificationUiEvent.StartCapture(DocumentPhotoTarget.COVER)) },
        )

        VerticalSpacer(dimensions.spaces.x4)

        DocumentCaptureCard(
            label = stringResource(uiState.documentBackPageLabel),
            capturedImageUri = formState.dataPhotoUri,
            placeholderImage = AppIcon.DataPage.resId,
            onClick = { event(DocumentVerificationUiEvent.StartCapture(DocumentPhotoTarget.DATA)) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentSubmissionStepEmptyPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(AppTheme.dimensions.spaces.x4)) {
            DocumentSubmissionStep(
                uiState = DocumentVerificationUiState(),
                formState = DocumentVerificationFormState(),
                event = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentSubmissionStepCoverCapturedPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(AppTheme.dimensions.spaces.x4)) {
            DocumentSubmissionStep(
                uiState = DocumentVerificationUiState(),
                formState = DocumentVerificationFormState(),
                event = {},
            )
        }
    }
}
