package com.extexis.otp.ui

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.ui.gds.ActionButton
import com.extexis.core.ui.gds.AppButton
import com.extexis.core.ui.gds.AppIconButton
import com.extexis.core.ui.gds.AppOtpField
import com.extexis.core.ui.gds.AppTextField
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme
import com.extexis.core.navigation.OtpPurpose
import com.extexis.core.ui.theme.ExtexisAndroidTheme
import com.extexis.otp.R

@Composable
fun OtpScreen(
    state: OtpState,
    event: (OtpUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = dimensions.spaces.x4)
    ) {
        Spacer(Modifier.height(dimensions.spaces.x4))

        AppIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { event(OtpUiEvent.BackClicked) },
            contentDescription = "Back",
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        Text(
            text = stringResource(R.string.otp_screen_title_verification_code),
            style = AppTheme.typography.Hero,
            color = colors.onBackground,
        )

        Spacer(Modifier.height(dimensions.spaces.x2))

        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.otp_screen_message_otp_sent))
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = colors.onBackground)) {
                    append(state.email)
                }
            },
            style = AppTextStyles.Body,
            color = colors.tertiary,
        )

        Spacer(Modifier.height(dimensions.spaces.x8))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AppOtpField(
                value = state.otp,
                onValueChange = { event(OtpUiEvent.OtpChanged(it)) },
                isError = state.otpError != null,
                modifier = Modifier.wrapContentSize(),
            )
        }

        if (state.otpError != null) {
            Spacer(Modifier.height(dimensions.spaces.x2))
            Text(
                text = state.otpError,
                style = AppTextStyles.Meta,
                color = colors.error,
            )
        }

        Spacer(Modifier.height(dimensions.spaces.x6))

        if (state.purpose == OtpPurpose.ForgotPassword) {
            Spacer(Modifier.height(dimensions.spaces.x6))

            AppTextField(
                value = state.newPassword,
                onValueChange = { event(OtpUiEvent.NewPasswordChanged(it)) },
                label = stringResource(R.string.otp_screen_label_new_password),
                isRequired = true,
                placeholder = stringResource(R.string.otp_screen_placeholder_enter_your_new_password),
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                isError = state.newPasswordError != null,
                errorMessage = state.newPasswordError,
            )

            Spacer(Modifier.height(dimensions.spaces.x4))

            AppTextField(
                value = state.confirmPassword,
                onValueChange = { event(OtpUiEvent.ConfirmPasswordChanged(it)) },
                label = stringResource(R.string.otp_screen_confirm_new_password),
                isRequired = true,
                placeholder = stringResource(R.string.otp_screen_placeholder_re_enter_your_new_password),
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                isError = state.confirmPasswordError != null,
                errorMessage = state.confirmPasswordError,
            )
        }

        Spacer(Modifier.height(dimensions.spaces.x8))

        AppButton(
            text = if (state.purpose == OtpPurpose.ForgotPassword)
                stringResource(R.string.otp_screen_cta_reset_password)
            else stringResource(R.string.otp_screen_cta_verify),
            onClick = { event(OtpUiEvent.VerifyClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.otp.length == 6 && !state.isLoading,
        )

        Spacer(Modifier.height(dimensions.spaces.x4))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (state.canResend)
                    stringResource(R.string.otp_screen_didn_t_receive_the_code)
                else stringResource(R.string.otp_screen_resend_code_in_s, state.resendTimer),
                style = AppTheme.typography.Body,
                color = colors.tertiary,
            )
            if (state.canResend) {
                ActionButton(
                    text = stringResource(R.string.otp_screen_cta_resend),
                    onClick = { event(OtpUiEvent.ResendClicked) },
                    style = AppTheme.typography.BodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OtpScreenPreview() {
    ExtexisAndroidTheme {
        OtpScreen(
            state = OtpState(email = "dd@gmail.com", purpose = OtpPurpose.Registration),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OtpScreenForgotPasswordPreview() {
    ExtexisAndroidTheme {
        OtpScreen(
            state = OtpState(
                email = "dd@gmail.com",
                purpose = OtpPurpose.ForgotPassword,
                otp = "123456",
                canResend = true,
            ),
            event = {},
        )
    }
}
