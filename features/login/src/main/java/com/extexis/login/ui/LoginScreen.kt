package com.extexis.login.ui

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.ui.gds.ActionButton
import com.extexis.core.ui.gds.AppButton
import com.extexis.core.ui.gds.AppIconButton
import com.extexis.core.ui.gds.AppTextField
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme
import com.extexis.core.ui.theme.ExtexisAndroidTheme
import com.extexis.login.R

@Composable
fun LoginScreen(
    state: LoginScreenUiState,
    formState: LoginFormState,
    event: (LoginUiEvent) -> Unit,
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
        Spacer(Modifier.height(dimensions.spaces.x4))

        AppIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { event(LoginUiEvent.BackClicked) },
            contentDescription = stringResource(R.string.back),
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        Text(
            text = stringResource(R.string.login_screen_title_log_in),
            style = AppTheme.typography.Hero,
            color = colors.onBackground,
        )

        Spacer(Modifier.height(dimensions.spaces.x2))

        Text(
            text = stringResource(R.string.login_screen_message),
            style = AppTextStyles.Body,
            color = colors.tertiary,
        )

        Spacer(Modifier.height(dimensions.spaces.x8))

        AppTextField(
            value = formState.email,
            onValueChange = { event(LoginUiEvent.EmailChanged(it)) },
            placeholder = stringResource(R.string.login_screen_placeholder_email_address),
            leadingIcon = Icons.Default.Email,
            isError = state.emailError != null,
            errorMessage = state.emailError?.asString(),
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = formState.password,
            onValueChange = { event(LoginUiEvent.PasswordChanged(it)) },
            placeholder = stringResource(R.string.login_screen_placeholder_password),
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            isError = state.passwordError != null,
            errorMessage = state.passwordError?.asString(),
        )

        Spacer(Modifier.height(dimensions.spaces.x2))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            ActionButton(
                text = stringResource(R.string.login_screen_cta_forgot_password),
                onClick = { event(LoginUiEvent.ForgotPasswordClicked) },
            )
        }

        Spacer(Modifier.height(dimensions.spaces.x8))

        AppButton(
            text = stringResource(R.string.login_screen_cta_login),
            onClick = { event(LoginUiEvent.LoginClicked) },
            modifier = Modifier.fillMaxWidth(),
            isLoading = state.isLoading,
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.login_screen_don_t_have_an_account),
                style = AppTextStyles.Body,
                color = colors.tertiary,
            )
            ActionButton(
                text = stringResource(R.string.login_screen_cta_sign_up_now),
                onClick = { event(LoginUiEvent.SignUpClicked) },
                style = AppTextStyles.BodyMedium,
            )
        }

        Spacer(Modifier.height(dimensions.spaces.x8))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    ExtexisAndroidTheme {
        LoginScreen(
            state = LoginScreenUiState(),
            formState = LoginFormState(),
            event = {},
        )
    }
}
