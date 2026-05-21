package com.estexis.kyc.passport.ui.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.estexis.core.ui.gds.DocumentCaptureCard
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.kyc.passport.ui.PassportPhotoTarget
import com.estexis.kyc.passport.ui.PassportVerificationFormState
import com.estexis.kyc.passport.ui.PassportVerificationUiEvent

@Composable
fun DocumentSubmissionStep(
    formState: PassportVerificationFormState,
    event: (PassportVerificationUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Take a clear photo of the front of your Passport",
            style = AppTextStyles.BodyText2Regular,
            color = colors.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        VerticalSpacer(dimensions.spaces.x6)

        DocumentCaptureCard(
            label = "Cover Page",
            captured = formState.coverPhotoCaptured,
            onClick = { event(PassportVerificationUiEvent.StartCapture(PassportPhotoTarget.COVER)) },
        )

        VerticalSpacer(dimensions.spaces.x4)

        DocumentCaptureCard(
            label = "Data Page",
            captured = formState.dataPhotoCaptured,
            onClick = { event(PassportVerificationUiEvent.StartCapture(PassportPhotoTarget.DATA)) },
        )
    }
}
