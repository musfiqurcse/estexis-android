package com.estexis.kyc.passport.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppTitleBar
import com.estexis.core.ui.gds.Step
import com.estexis.core.ui.gds.StepIndicator
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.kyc.passport.ui.steps.CameraCaptureStep
import com.estexis.kyc.passport.ui.steps.DataCollectionStep
import com.estexis.kyc.passport.ui.steps.DocumentSubmissionStep

private val WIZARD_STEPS = listOf(
    Step(1, "Data\nCollection"),
    Step(2, "Document\nSubmission"),
)

@Composable
fun PassportVerificationScreen(
    uiState: PassportVerificationUiState,
    formState: PassportVerificationFormState,
    event: (PassportVerificationUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary)
            .statusBarsPadding()
            .imePadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensions.spaces.x4),
        ) {
            VerticalSpacer(dimensions.spaces.x2)

            AppTitleBar(
                onBackClick = { event(PassportVerificationUiEvent.BackClicked) },
                title = "Passport Verification",
            )

            VerticalSpacer(dimensions.spaces.x4)

            StepIndicator(
                steps = WIZARD_STEPS,
                currentStep = uiState.currentStep,
            )

            VerticalSpacer(dimensions.spaces.x6)

            when {
                uiState.isCaptureMode -> CameraCaptureStep()
                uiState.currentStep == 1 -> DataCollectionStep(
                    formState = formState,
                    uiState = uiState,
                    event = event,
                )
                else -> DocumentSubmissionStep(formState = formState, event = event)
            }

            VerticalSpacer(dimensions.spaces.x6)
        }

        BottomBar(uiState = uiState, formState = formState, event = event)
    }
}

@Composable
private fun BottomBar(
    uiState: PassportVerificationUiState,
    formState: PassportVerificationFormState,
    event: (PassportVerificationUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    val text: String
    val onClick: () -> Unit
    val enabled: Boolean

    when {
        uiState.isCaptureMode -> {
            text = "Take Photo"
            onClick = { event(PassportVerificationUiEvent.TakePhotoClicked) }
            enabled = true
        }
        uiState.currentStep == 1 -> {
            text = "Continue"
            onClick = { event(PassportVerificationUiEvent.ContinueClicked) }
            enabled = true
        }
        else -> {
            text = "Submit"
            onClick = { event(PassportVerificationUiEvent.SubmitClicked) }
            enabled = formState.coverPhotoCaptured && formState.dataPhotoCaptured
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
        PassportVerificationScreen(
            uiState = PassportVerificationUiState(currentStep = 1),
            formState = PassportVerificationFormState(),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentSubmissionEmptyPreview() {
    ExtexisAndroidTheme {
        PassportVerificationScreen(
            uiState = PassportVerificationUiState(currentStep = 2),
            formState = PassportVerificationFormState(),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentSubmissionCapturedPreview() {
    ExtexisAndroidTheme {
        PassportVerificationScreen(
            uiState = PassportVerificationUiState(currentStep = 2),
            formState = PassportVerificationFormState(
                coverPhotoCaptured = true,
                dataPhotoCaptured = true,
            ),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CameraCapturePreview() {
    ExtexisAndroidTheme {
        PassportVerificationScreen(
            uiState = PassportVerificationUiState(
                currentStep = 2,
                captureMode = PassportPhotoTarget.COVER,
            ),
            formState = PassportVerificationFormState(),
            event = {},
        )
    }
}
