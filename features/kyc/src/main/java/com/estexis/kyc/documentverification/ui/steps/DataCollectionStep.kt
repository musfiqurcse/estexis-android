package com.estexis.kyc.documentverification.ui.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.gds.AppTextField
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.core.ui.util.UiText
import com.estexis.kyc.R
import com.estexis.kyc.documentverification.ui.DocumentVerificationFormState
import com.estexis.kyc.documentverification.ui.DocumentVerificationUiState
import com.estexis.kyc.documentverification.ui.PassportVerificationUiEvent

@Composable
fun DataCollectionStep(
    formState: DocumentVerificationFormState,
    uiState: DocumentVerificationUiState,
    event: (PassportVerificationUiEvent) -> Unit,
) {
    val dimensions = AppTheme.dimensions

    Column(modifier = Modifier.fillMaxWidth()) {

        AppTextField(
            value = formState.documentNumber,
            onValueChange = { event(PassportVerificationUiEvent.PassportNumberChanged(it)) },
            label = stringResource(uiState.documentNumberLabel),
            placeholder = stringResource(uiState.documentNumberPlaceholder),
            isRequired = true,
            isError = uiState.documentNumberError != null,
            errorMessage = uiState.documentNumberError?.asString(),
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.dateOfBirth,
            onValueChange = { event(PassportVerificationUiEvent.DateOfBirthChanged(it)) },
            label = stringResource(R.string.document_verification_screen_date_of_birth),
            placeholder = stringResource(R.string.document_verification_screen_select_date),
            isRequired = true,
            isError = uiState.dateOfBirthError != null,
            errorMessage = uiState.dateOfBirthError?.asString(),
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.expiryDate,
            onValueChange = { event(PassportVerificationUiEvent.ExpiryDateChanged(it)) },
            label = stringResource(R.string.document_verification_screen_expiry_date),
            placeholder = stringResource(R.string.document_verification_screen_enter_expiry_date),
            isRequired = true,
            isError = uiState.expiryDateError != null,
            errorMessage = uiState.expiryDateError?.asString(),
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.issueDate,
            onValueChange = { event(PassportVerificationUiEvent.IssueDateChanged(it)) },
            label = stringResource(R.string.document_verification_screen_issue_date),
            placeholder = stringResource(R.string.document_verification_screen_enter_issue_date),
            isRequired = true,
            isError = uiState.issueDateError != null,
            errorMessage = uiState.issueDateError?.asString(),
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.countryOfIssue,
            onValueChange = { event(PassportVerificationUiEvent.CountryChanged(it)) },
            label = stringResource(R.string.document_verification_screen_country_of_issue),
            placeholder = stringResource(R.string.document_verification_screen_select_country_name),
            isRequired = true,
            isError = uiState.countryError != null,
            errorMessage = uiState.countryError?.asString(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DataCollectionStepEmptyPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            DataCollectionStep(
                formState = DocumentVerificationFormState(),
                uiState = DocumentVerificationUiState(),
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
                formState = DocumentVerificationFormState(
                    documentNumber = "A01234567",
                    dateOfBirth = "01/01/1990",
                    expiryDate = "01/01/2030",
                    issueDate = "01/01/2020",
                    countryOfIssue = "Bangladesh",
                ),
                uiState = DocumentVerificationUiState(),
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
                formState = DocumentVerificationFormState(),
                uiState = DocumentVerificationUiState(
                    documentNumberError = UiText.StringResource(R.string.passport_verification_screen_passport_number_needed),
                    dateOfBirthError = UiText.StringResource(R.string.passport_verification_screen_dob_needed),
                    expiryDateError = UiText.StringResource(R.string.passport_verification_screen_exp_date_needed),
                    issueDateError = UiText.StringResource(R.string.passport_verification_screen_issue_date_needed),
                    countryError = UiText.StringResource(R.string.passport_verification_screen_country_of_issue_needed),
                ),
                event = {},
            )
        }
    }
}
