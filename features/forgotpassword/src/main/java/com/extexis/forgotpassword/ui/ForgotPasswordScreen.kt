package com.extexis.forgotpassword.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.ui.gds.AppButton
import com.extexis.core.ui.gds.AppIconButton
import com.extexis.core.ui.gds.AppTextField
import com.extexis.core.ui.gds.VerticalSpacer
import com.extexis.core.ui.theme.AppTheme
import com.extexis.core.ui.theme.ExtexisAndroidTheme
import com.extexis.forgotpassword.R

@Composable
fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    event: (ForgotPasswordUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensions.spaces.x4)
            .imePadding(),
    ) {
        VerticalSpacer(dimensions.spaces.x4)

        AppIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { event(ForgotPasswordUiEvent.BackClicked) },
            contentDescription = "Back",
        )

        VerticalSpacer(dimensions.spaces.x6)

        Text(
            text = stringResource(R.string.forgot_password_screen_title),
            style = AppTheme.typography.H2SemiBold,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x2)

        Text(
            text = stringResource(R.string.forgot_password_screen_message),
            style = AppTheme.typography.BodyText2Regular,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x8)

        AppTextField(
            value = state.email,
            onValueChange = { event(ForgotPasswordUiEvent.EmailChanged(it)) },
            label = stringResource(R.string.forgot_password_screen_label_email),
            isRequired = true,
            placeholder = stringResource(R.string.forgot_password_screen_placeholder),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = state.emailError != null,
            errorMessage = state.emailError?.asString(),
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = state.lastName,
            onValueChange = { event(ForgotPasswordUiEvent.LastNameChanged(it)) },
            label = stringResource(R.string.forgot_password_screen_label_last_name),
            isRequired = true,
            placeholder = stringResource(R.string.forgot_password_screen_placeholder_last_name),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = state.emailError != null,
            errorMessage = state.lastNameError?.asString(),
        )

        VerticalSpacer(dimensions.spaces.x8)

        AppButton(
            text = stringResource(R.string.forgot_password_screen_cta_send_code),
            onClick = { event(ForgotPasswordUiEvent.SubmitClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordScreenPreview() {
    ExtexisAndroidTheme {
        ForgotPasswordScreen(
            state = ForgotPasswordState(),
            event = {},
        )
    }
}
