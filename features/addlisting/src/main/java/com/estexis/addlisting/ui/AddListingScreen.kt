package com.estexis.addlisting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.addlisting.ui.steps.BasicInfoStep
import com.estexis.addlisting.ui.steps.DetailsStep
import com.estexis.addlisting.ui.steps.MediaStep
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppButtonVariant
import com.estexis.core.ui.gds.Step
import com.estexis.core.ui.gds.StepIndicator
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.core.ui.util.addBackground

private val WIZARD_STEPS = listOf(
    Step(1, "Basic\ninfo"),
    Step(2, "Details"),
    Step(3, "Media"),
)

@Composable
fun AddListingScreen(
    uiState: AddListingUiState,
    formState: AddListingFormState,
    event: (AddListingUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .addBackground(
                hasVerticalScroll = false
            )
            .imePadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {

            StepIndicator(
                steps = WIZARD_STEPS,
                currentStep = uiState.currentStep,
            )

            VerticalSpacer(dimensions.spaces.x4)

            when (uiState.currentStep) {
                1 -> BasicInfoStep(formState, event)
                2 -> DetailsStep(formState, event)
                3 -> MediaStep(formState, event)
            }

            VerticalSpacer(dimensions.spaces.x6)
        }

        BottomBar(uiState, event)
    }
}

@Composable
private fun BottomBar(
    uiState: AddListingUiState,
    event: (AddListingUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    val isLastStep = uiState.currentStep == AddListingViewModel.TOTAL_STEPS

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.onPrimary)
            .padding(bottom = dimensions.spaces.x2)
        ,
        horizontalArrangement = Arrangement.spacedBy(dimensions.spaces.x3),
    ) {
        AppButton(
            text = if (isLastStep) "Save Draft" else "Back",
            onClick = { event(if (isLastStep) AddListingUiEvent.SaveDraftClicked else AddListingUiEvent.BackClicked) },
            variant = AppButtonVariant.SECONDARY,
            enabled = isLastStep || uiState.currentStep > 1,
            modifier = Modifier.weight(1f),
        )
        AppButton(
            text = if (isLastStep) "Publish Listing" else "Continue",
            onClick = { event(if (isLastStep) AddListingUiEvent.PublishClicked else AddListingUiEvent.ContinueClicked) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddListingScreenStep1Preview() {
    ExtexisAndroidTheme {
        AddListingScreen(
            uiState = AddListingUiState(currentStep = 1),
            formState = AddListingFormState(),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddListingScreenStep2Preview() {
    ExtexisAndroidTheme {
        AddListingScreen(
            uiState = AddListingUiState(currentStep = 2),
            formState = AddListingFormState(),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddListingScreenStep3Preview() {
    ExtexisAndroidTheme {
        AddListingScreen(
            uiState = AddListingUiState(currentStep = 3),
            formState = AddListingFormState(
                mediaUris = listOf("a", "b", "c", "d", "e"),
            ),
            event = {},
        )
    }
}
