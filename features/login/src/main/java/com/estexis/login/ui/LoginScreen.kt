package com.estexis.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.gds.ActionButton
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppIcon
import com.estexis.core.ui.gds.AppTextField
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.login.R

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
        VerticalSpacer(dimensions.spaces.x16)

        Text(
            text = stringResource(R.string.login_screen_title_log_in),
            style = AppTheme.typography.H2Bold,
            color = colors.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpacer(dimensions.spaces.x2)

        Text(
            text = stringResource(R.string.login_screen_message),
            style = AppTextStyles.BodyText3Regular,
            color = colors.tertiary,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        VerticalSpacer(dimensions.spaces.x8)

        AppTextField(
            value = formState.email,
            onValueChange = { event(LoginUiEvent.EmailChanged(it)) },
            placeholder = stringResource(R.string.login_screen_placeholder_email_address),
            leadingIcon = AppIcon.IcMail.resId,
            isError = state.emailError != null,
            errorMessage = state.emailError?.asString(),
        )

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.password,
            onValueChange = { event(LoginUiEvent.PasswordChanged(it)) },
            placeholder = stringResource(R.string.login_screen_placeholder_password),
            leadingIcon = AppIcon.IcLockPassword.resId,
            isPassword = true,
            isError = state.passwordError != null,
            errorMessage = state.passwordError?.asString(),
        )

        VerticalSpacer(dimensions.spaces.x4)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            ActionButton(
                text = stringResource(R.string.login_screen_cta_forgot_password),
                onClick = { event(LoginUiEvent.ForgotPasswordClicked) },
            )
        }

        VerticalSpacer(dimensions.spaces.x8)

        AppButton(
            text = stringResource(R.string.login_screen_cta_login),
            onClick = { event(LoginUiEvent.LoginClicked) },
            modifier = Modifier.fillMaxWidth(),
            isLoading = state.isLoading,
        )

        VerticalSpacer(dimensions.spaces.x6)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.login_screen_don_t_have_an_account),
                style = AppTextStyles.BodyText3Regular,
                color = colors.onBackground,
            )
            ActionButton(
                text = stringResource(R.string.login_screen_cta_sign_up_now),
                onClick = { event(LoginUiEvent.SignUpClicked) },
                style = AppTextStyles.BodyText3Bold,
            )
        }

        VerticalSpacer(dimensions.spaces.x8)
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
