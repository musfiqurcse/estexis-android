package com.estexis.kyc.passport.ui.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.gds.AppTextField
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
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
            isRequired = true,
            isError = uiState.passportNumberError != null,
            errorMessage = uiState.passportNumberError,
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.dateOfBirth,
            onValueChange = { event(PassportVerificationUiEvent.DateOfBirthChanged(it)) },
            label = "Date of Birth",
            placeholder = "Select Date",
            isRequired = true,
            isError = uiState.dateOfBirthError != null,
            errorMessage = uiState.dateOfBirthError,
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.expiryDate,
            onValueChange = { event(PassportVerificationUiEvent.ExpiryDateChanged(it)) },
            label = "Expiry Date",
            placeholder = "Enter Expiry Date",
            isRequired = true,
            isError = uiState.expiryDateError != null,
            errorMessage = uiState.expiryDateError,
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.issueDate,
            onValueChange = { event(PassportVerificationUiEvent.IssueDateChanged(it)) },
            label = "Issue Date",
            placeholder = "Enter Issue Date",
            isRequired = true,
            isError = uiState.issueDateError != null,
            errorMessage = uiState.issueDateError,
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.countryOfIssue,
            onValueChange = { event(PassportVerificationUiEvent.CountryChanged(it)) },
            label = "Country of Issue",
            placeholder = "Enter Country Name",
            isRequired = true,
            isError = uiState.countryError != null,
            errorMessage = uiState.countryError,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DataCollectionStepEmptyPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DataCollectionStep(
                formState = PassportVerificationFormState(),
                uiState = PassportVerificationUiState(),
                event = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DataCollectionStepFilledPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DataCollectionStep(
                formState = PassportVerificationFormState(
                    passportNumber = "A01234567",
                    dateOfBirth = "01/01/1990",
                    expiryDate = "01/01/2030",
                    issueDate = "01/01/2020",
                    countryOfIssue = "Bangladesh",
                ),
                uiState = PassportVerificationUiState(),
                event = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DataCollectionStepErrorsPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DataCollectionStep(
                formState = PassportVerificationFormState(),
                uiState = PassportVerificationUiState(
                    passportNumberError = "Passport number is required",
                    dateOfBirthError = "Date of birth is required",
                    expiryDateError = "Expiry date is required",
                    issueDateError = "Issue date is required",
                    countryError = "Country is required",
                ),
                event = {},
            )
        }
    }
}
