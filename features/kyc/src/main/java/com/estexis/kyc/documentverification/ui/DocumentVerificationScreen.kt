package com.estexis.kyc.documentverification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppTitleBar
import com.estexis.core.ui.gds.Step
import com.estexis.core.ui.gds.StepIndicator
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.core.ui.util.addBackground
import com.estexis.kyc.R
import com.estexis.kyc.documentverification.ui.steps.CameraCaptureStep
import com.estexis.kyc.documentverification.ui.steps.DataCollectionStep
import com.estexis.kyc.documentverification.ui.steps.DocumentSubmissionStep
import com.estexis.kyc.documentverification.ui.steps.rememberCaptureController

private val WIZARD_STEPS = listOf(
    Step(1, "Data\nCollection"),
    Step(2, "Document\nSubmission"),
)

@Composable
fun DocumentVerificationScreen(
    uiState: DocumentVerificationUiState,
    formState: DocumentVerificationFormState,
    event: (PassportVerificationUiEvent) -> Unit,
) {
    val dimensions = AppTheme.dimensions
    val captureController = rememberCaptureController()

    Column(
        modifier = Modifier
            .addBackground()
            .imePadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            VerticalSpacer(dimensions.spaces.x2)

            AppTitleBar(
                onBackClick = { event(PassportVerificationUiEvent.BackClicked) },
                title = stringResource(uiState.title)
            )

            VerticalSpacer(dimensions.spaces.x4)

            StepIndicator(
                steps = WIZARD_STEPS,
                currentStep = uiState.currentStep,
            )

            VerticalSpacer(dimensions.spaces.x6)

            when {
                uiState.isCaptureMode -> CameraCaptureStep(
                    captureController = captureController,
                    onPhotoTaken = { uri -> event(PassportVerificationUiEvent.PhotoTaken(uri)) },
                )
                uiState.currentStep == 1 -> DataCollectionStep(
                    formState = formState,
                    uiState = uiState,
                    event = event,
                )

                else -> DocumentSubmissionStep(
                    uiState = uiState,
                    formState = formState,
                    event = event
                )
            }

            VerticalSpacer(dimensions.spaces.x6)
        }

        BottomBar(
            uiState = uiState,
            formState = formState,
            event = event,
            onTakePhotoClick = { captureController.capture() },
        )
    }
}

@Composable
private fun BottomBar(
    uiState: DocumentVerificationUiState,
    formState: DocumentVerificationFormState,
    event: (PassportVerificationUiEvent) -> Unit,
    onTakePhotoClick: () -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    val text: String
    val onClick: () -> Unit
    val enabled: Boolean

    when {
        uiState.isCaptureMode -> {
            text = stringResource(R.string.document_verification_screen_cta_take_photo)
            onClick = onTakePhotoClick
            enabled = true
        }

        uiState.currentStep == 1 -> {
            text = stringResource(R.string.document_verification_screen_cta_continue)
            onClick = { event(PassportVerificationUiEvent.ContinueClicked) }
            enabled = true
        }

        else -> {
            text = stringResource(R.string.document_verification_screen_cta_submit)
            onClick = { event(PassportVerificationUiEvent.SubmitClicked) }
            enabled = formState.coverPhotoUri != null && formState.dataPhotoUri != null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.onPrimary)
            .padding(horizontal = dimensions.spaces.x4, vertical = dimensions.spaces.x4),
    ) {
        AppButton(
            text = text,
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            isLoading = uiState.isLoading,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DataCollectionPreview() {
    ExtexisAndroidTheme {
        DocumentVerificationScreen(
            uiState = DocumentVerificationUiState(currentStep = 1),
            formState = DocumentVerificationFormState(),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentSubmissionEmptyPreview() {
    ExtexisAndroidTheme {
        DocumentVerificationScreen(
            uiState = DocumentVerificationUiState(currentStep = 2),
            formState = DocumentVerificationFormState(),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CameraCapturePreview() {
    ExtexisAndroidTheme {
        DocumentVerificationScreen(
            uiState = DocumentVerificationUiState(
                currentStep = 2,
                captureMode = DocumentPhotoTarget.COVER,
            ),
            formState = DocumentVerificationFormState(),
            event = {},
        )
    }
}
