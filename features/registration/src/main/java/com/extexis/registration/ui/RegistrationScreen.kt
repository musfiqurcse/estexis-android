package com.extexis.registration.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.ui.gds.ActionButton
import com.extexis.core.ui.gds.AppButton
import com.extexis.core.ui.gds.AppIconButton
import com.extexis.core.ui.gds.AppTextField
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme
import com.extexis.core.ui.theme.ExtexisAndroidTheme
import com.extexis.registration.R
import com.extexis.registration.domain.availableCountries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    state: RegistrationState,
    formState: RegistrationFormState,
    hostState: SnackbarHostState,
    event: (RegistrationUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it != SheetValue.Hidden || !state.isLoading },
    )

    if (state.showCountryPicker) {
        CountryPickerBottomSheet(
            countries = availableCountries,
            selectedCountry = formState.selectedCountry,
            sheetState = sheetState,
            onCountrySelected = { event(RegistrationUiEvent.CountrySelected(it)) },
            onDismiss = { event(RegistrationUiEvent.DismissCountryPicker) },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary)
            .padding(horizontal = dimensions.spaces.x4)
            .statusBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(dimensions.spaces.x4))

        AppIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { event(RegistrationUiEvent.BackClicked) },
            contentDescription = "Back",
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        Text(
            text = stringResource(R.string.registration_screen_create_an_account),
            style = AppTheme.typography.Hero,
            color = colors.onBackground,
        )

        Spacer(Modifier.height(dimensions.spaces.x2))

        Text(
            text = stringResource(R.string.registration_screen_welcome_please_enter_your_details),
            style = AppTextStyles.Body,
            color = colors.tertiary,
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        AppTextField(
            value = formState.firstName,
            onValueChange = { event(RegistrationUiEvent.FirstNameChanged(it)) },
            label = stringResource(R.string.registration_screen_label_first_name),
            isRequired = true,
            placeholder = stringResource(R.string.registration_screen_placeholder_enter_your_first_name),
            leadingIcon = Icons.Default.Person,
            isError = state.firstNameError != null,
            errorMessage = state.firstNameError?.asString(),
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = formState.lastName,
            onValueChange = { event(RegistrationUiEvent.LastNameChanged(it)) },
            label = stringResource(R.string.registration_screen_label_last_name),
            isRequired = true,
            placeholder = stringResource(R.string.registration_screen_placeholder_enter_your_last_name),
            leadingIcon = Icons.Default.Person,
            isError = state.lastNameError != null,
            errorMessage = state.lastNameError?.asString(),
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = formState.email,
            onValueChange = { event(RegistrationUiEvent.EmailChanged(it)) },
            label = stringResource(R.string.registration_screen_label_email),
            isRequired = true,
            placeholder = stringResource(R.string.registration_screen_placeholder_enter_your_email),
            leadingIcon = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = state.emailError != null,
            errorMessage = state.emailError?.asString(),
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = formState.phoneNumber,
            onValueChange = { event(RegistrationUiEvent.PhoneNumberChanged(it)) },
            label = stringResource(R.string.registration_screen_label_phone_number),
            isRequired = true,
            placeholder = stringResource(R.string.registration_screen_placeholder_enter_your_phone_number),
            leadingIcon = Icons.Default.Phone,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = state.phoneNumberError != null,
            errorMessage = state.phoneNumberError?.asString(),
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        CountryPickerFieldView(
            selected = formState.selectedCountry?.let { "${it.flag}  ${it.name}" },
            isError = state.countryError != null,
            errorMessage = state.countryError?.asString(),
            onClick = { event(RegistrationUiEvent.ShowCountryPicker) },
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = formState.password,
            onValueChange = { event(RegistrationUiEvent.PasswordChanged(it)) },
            label = stringResource(R.string.registration_screen_label_password),
            isRequired = true,
            placeholder = stringResource(R.string.registration_screen_placeholder_enter_your_password),
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            isError = state.passwordError != null,
            errorMessage = state.passwordError?.asString(),
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = formState.confirmPassword,
            onValueChange = { event(RegistrationUiEvent.ConfirmPasswordChanged(it)) },
            label = stringResource(R.string.registration_screen_label_confirm_password),
            isRequired = true,
            placeholder = stringResource(R.string.registration_screen_placeholder_re_enter_your_password),
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            isError = state.confirmPasswordError != null,
            errorMessage = state.confirmPasswordError?.asString(),
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        RoleSelectorView(
            selected = formState.selectedRole,
            isError = state.roleError != null,
            errorMessage = state.roleError?.asString(),
            onRoleSelected = { event(RegistrationUiEvent.RoleChanged(it)) },
        )

        Spacer(Modifier.height(dimensions.spaces.x8))

        AppButton(
            text = stringResource(R.string.registration_screen_cta_sign_up),
            onClick = { event(RegistrationUiEvent.SignUpClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.registration_screen_already_have_an_account),
                style = AppTextStyles.Body,
                color = colors.tertiary,
            )
            ActionButton(
                text = stringResource(R.string.registration_screen_cta_login),
                onClick = { event(RegistrationUiEvent.LoginClicked) },
                style = AppTextStyles.BodyMedium,
            )
        }

        Spacer(Modifier.height(dimensions.spaces.x8))
    }
}


@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    ExtexisAndroidTheme {
        RegistrationScreen(
            state = RegistrationState(),
            formState = RegistrationFormState(),
            hostState = SnackbarHostState(),
            event = {},
        )
    }
}
