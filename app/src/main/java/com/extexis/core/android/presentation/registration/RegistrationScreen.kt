package com.extexis.core.android.presentation.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.android.gds.ActionButton
import com.extexis.core.android.gds.AppButton
import com.extexis.core.android.gds.AppIconButton
import com.extexis.core.android.gds.AppTextField
import com.extexis.core.android.ui.theme.AppTextStyles
import com.extexis.core.android.ui.theme.AppTheme
import com.extexis.core.android.ui.theme.ExtexisAndroidTheme

@Composable
fun RegistrationScreen(
    state: RegistrationState,
    event: (RegistrationUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surface)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensions.spaces.x6)
            .imePadding(),
    ) {
        Spacer(Modifier.height(dimensions.spaces.x4))

        AppIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { event(RegistrationUiEvent.BackClicked) },
            contentDescription = "Back",
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        Text(
            text = "Create an account",
            style = AppTheme.typography.Hero,
            color = colors.onBackground,
        )

        Spacer(Modifier.height(dimensions.spaces.x2))

        Text(
            text = "Welcome! Please enter your details.",
            style = AppTextStyles.Body,
            color = colors.tertiary,
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        AppTextField(
            value = state.email,
            onValueChange = { event(RegistrationUiEvent.EmailChanged(it)) },
            label = "Email",
            isRequired = true,
            placeholder = "Enter your email",
            leadingIcon = Icons.Default.Email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = state.emailError != null,
            errorMessage = state.emailError,
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = state.password,
            onValueChange = { event(RegistrationUiEvent.PasswordChanged(it)) },
            label = "Password",
            isRequired = true,
            placeholder = "Enter your password",
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            isError = state.passwordError != null,
            errorMessage = state.passwordError,
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = state.confirmPassword,
            onValueChange = { event(RegistrationUiEvent.ConfirmPasswordChanged(it)) },
            label = "Confirm password",
            isRequired = true,
            placeholder = "Re-enter your password",
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            isError = state.confirmPasswordError != null,
            errorMessage = state.confirmPasswordError,
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = state.firstName,
            onValueChange = { event(RegistrationUiEvent.FirstNameChanged(it)) },
            label = "First name",
            isRequired = true,
            placeholder = "Enter your first name",
            leadingIcon = Icons.Default.Person,
            isError = state.firstNameError != null,
            errorMessage = state.firstNameError,
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = state.lastName,
            onValueChange = { event(RegistrationUiEvent.LastNameChanged(it)) },
            label = "Last name",
            isRequired = true,
            placeholder = "Enter your last name",
            leadingIcon = Icons.Default.Person,
            isError = state.lastNameError != null,
            errorMessage = state.lastNameError,
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = state.phoneNumber,
            onValueChange = { event(RegistrationUiEvent.PhoneNumberChanged(it)) },
            label = "Phone number",
            isRequired = true,
            placeholder = "Enter your phone number",
            leadingIcon = Icons.Default.Phone,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = state.phoneNumberError != null,
            errorMessage = state.phoneNumberError,
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = state.company,
            onValueChange = { event(RegistrationUiEvent.CompanyChanged(it)) },
            label = "Company",
            placeholder = "Enter your company name",
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = state.contactPersonName,
            onValueChange = { event(RegistrationUiEvent.ContactPersonNameChanged(it)) },
            label = "Contact person name",
            placeholder = "Enter your contact person name",
        )

        Spacer(Modifier.height(dimensions.spaces.x8))

        AppButton(
            text = "Sign Up",
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
                text = "Already have an account? ",
                style = AppTextStyles.Body,
                color = colors.tertiary,
            )
            ActionButton(
                text = "Login",
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
            event = {},
        )
    }
}
