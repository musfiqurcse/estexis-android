package com.estexis.kyc.documentverification.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppLoadingDialog
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

@Composable
fun DocumentVerificationScreen(
    uiState: DocumentVerificationUiState,
    formState: DocumentVerificationFormState,
    event: (DocumentVerificationUiEvent) -> Unit,
) {
    val dimensions = AppTheme.dimensions
    val captureController = rememberCaptureController()
    val wizardSteps = remember {
        listOf(
            Step(1, "Data\nCollection"),
            Step(2, "Document\nSubmission"),
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                onBackClick = { event(DocumentVerificationUiEvent.BackClicked) },
                title = stringResource(uiState.title)
            )

            VerticalSpacer(dimensions.spaces.x4)

            StepIndicator(
                steps = wizardSteps,
                currentStep = uiState.currentStep,
            )

            VerticalSpacer(dimensions.spaces.x6)

            when {
                uiState.isCaptureMode -> CameraCaptureStep(
                    captureController = captureController,
                    onPhotoTaken = { uri -> event(DocumentVerificationUiEvent.PhotoTaken(uri)) },
                )
                uiState.currentStep == 1 -> DataCollectionStep(
                    formState = formState,
                    uiState = uiState,
                    event = event,
                )
                uiState.currentStep == 2 -> DocumentSubmissionStep(
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

    if (uiState.isSubmitting) {
        AppLoadingDialog()
    }
    } // end Box
}

@Composable
private fun BottomBar(
    uiState: DocumentVerificationUiState,
    formState: DocumentVerificationFormState,
    event: (DocumentVerificationUiEvent) -> Unit,
    onTakePhotoClick: () -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    val text: String
    val onClick: () -> Unit
    val enabled: Boolean

    val docPhotosCaptured = formState.coverPhotoUri != null && formState.dataPhotoUri != null

    when {
        uiState.isCaptureMode -> {
            text = stringResource(R.string.document_verification_screen_cta_take_photo)
            onClick = onTakePhotoClick
            enabled = true
        }

        uiState.isLastStep -> {
            text = stringResource(R.string.document_verification_screen_cta_submit)
            onClick = { event(DocumentVerificationUiEvent.SubmitClicked) }
            enabled = docPhotosCaptured
        }

        uiState.currentStep == 2 -> {
            text = stringResource(R.string.document_verification_screen_cta_continue)
            onClick = { event(DocumentVerificationUiEvent.ContinueClicked) }
            enabled = docPhotosCaptured
        }

        else -> {
            text = stringResource(R.string.document_verification_screen_cta_continue)
            onClick = { event(DocumentVerificationUiEvent.ContinueClicked) }
            enabled = true
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
