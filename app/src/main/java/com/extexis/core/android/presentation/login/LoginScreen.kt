package com.extexis.core.android.presentation.login

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
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.android.gds.ActionButton
import com.extexis.core.android.gds.AppButton
import com.extexis.core.android.gds.AppIconButton
import com.extexis.core.android.gds.AppTextField
import com.extexis.core.android.ui.theme.AppTextStyles
import com.extexis.core.android.ui.theme.AppTheme
import com.extexis.core.android.ui.theme.ExtexisAndroidTheme

@Composable
fun LoginScreen(
    state: LoginState,
    event: (LoginUiEvent) -> Unit,
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
            onClick = { event(LoginUiEvent.BackClicked) },
            contentDescription = "Back",
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        Text(
            text = "Log in",
            style = AppTheme.typography.Hero,
            color = colors.onBackground,
        )

        Spacer(Modifier.height(dimensions.spaces.x2))

        Text(
            text = "Enter your email and password to securely access your account and manage your service.",
            style = AppTextStyles.Body,
            color = colors.tertiary,
        )

        Spacer(Modifier.height(dimensions.spaces.x8))

        AppTextField(
            value = state.email,
            onValueChange = { event(LoginUiEvent.EmailChanged(it)) },
            placeholder = "Email address",
            leadingIcon = Icons.Default.Email,
            isError = state.emailError != null,
            errorMessage = state.emailError,
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        AppTextField(
            value = state.password,
            onValueChange = { event(LoginUiEvent.PasswordChanged(it)) },
            placeholder = "Password",
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            isError = state.passwordError != null,
            errorMessage = state.passwordError,
        )

        Spacer(Modifier.height(dimensions.spaces.x2))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            ActionButton(
                text = "Forgot Password?",
                onClick = { event(LoginUiEvent.ForgotPasswordClicked) },
            )
        }

        Spacer(Modifier.height(dimensions.spaces.x8))

        AppButton(
            text = "Login",
            onClick = { event(LoginUiEvent.LoginClicked) },
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
                text = "Don't have an account? ",
                style = AppTextStyles.Body,
                color = colors.tertiary,
            )
            ActionButton(
                text = "Sign up now",
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
            state = LoginState(),
            event = {},
        )
    }
}
