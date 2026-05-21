package com.estexis.kyc.passport.ui.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.estexis.core.ui.gds.AppTextField
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTheme
import com.estexis.kyc.passport.ui.PassportVerificationFormState
import com.estexis.kyc.passport.ui.PassportVerificationUiEvent
import com.estexis.kyc.passport.ui.PassportVerificationUiState

@Composable
fun DataCollectionStep(
    formState: PassportVerificationFormState,
    uiState: PassportVerificationUiState,
    event: (PassportVerificationUiEvent) -> Unit,
) {
    val dimensions = AppTheme.dimensions

    Column(modifier = Modifier.fillMaxWidth()) {
        AppTextField(
            value = formState.passportNumber,
            onValueChange = { event(PassportVerificationUiEvent.PassportNumberChanged(it)) },
            label = "Passport Number",
            placeholder = "Enter Passport Number",
            isError = uiState.passportNumberError != null,
            errorMessage = uiState.passportNumberError,
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.dateOfBirth,
            onValueChange = { event(PassportVerificationUiEvent.DateOfBirthChanged(it)) },
            label = "Date of Birth",
            placeholder = "Select Date",
            isError = uiState.dateOfBirthError != null,
            errorMessage = uiState.dateOfBirthError,
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.expiryDate,
            onValueChange = { event(PassportVerificationUiEvent.ExpiryDateChanged(it)) },
            label = "Expiry Date",
            placeholder = "Enter Expiry Date",
            isError = uiState.expiryDateError != null,
            errorMessage = uiState.expiryDateError,
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.issueDate,
            onValueChange = { event(PassportVerificationUiEvent.IssueDateChanged(it)) },
            label = "Issue Date",
            placeholder = "Enter Issue Date",
            isError = uiState.issueDateError != null,
            errorMessage = uiState.issueDateError,
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.countryOfIssue,
            onValueChange = { event(PassportVerificationUiEvent.CountryChanged(it)) },
            label = "Country of Issue",
            placeholder = "Enter Country Name",
            isError = uiState.countryError != null,
            errorMessage = uiState.countryError,
        )
    }
}
