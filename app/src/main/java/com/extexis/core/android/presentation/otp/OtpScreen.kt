package com.extexis.core.android.presentation.otp

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.android.gds.ActionButton
import com.extexis.core.android.gds.AppButton
import com.extexis.core.android.gds.AppIconButton
import com.extexis.core.android.gds.AppOtpField
import com.extexis.core.android.gds.AppTextField
import com.extexis.core.android.ui.theme.AppTextStyles
import com.extexis.core.android.ui.theme.AppTheme
import com.extexis.core.android.ui.theme.ExtexisAndroidTheme

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
            .background(colors.surface)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensions.spaces.x6)
            .imePadding(),
    ) {
        Spacer(Modifier.height(dimensions.spaces.x4))

        AppIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { event(OtpUiEvent.BackClicked) },
            contentDescription = "Back",
        )

        Spacer(Modifier.height(dimensions.spaces.x6))

        Text(
            text = "Verification code",
            style = AppTheme.typography.Hero,
            color = colors.onBackground,
        )

        Spacer(Modifier.height(dimensions.spaces.x2))

        Text(
            text = buildAnnotatedString {
                append("Enter the 6-digit verification code we sent to ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = colors.onBackground)) {
                    append(state.email)
                }
            },
            style = AppTextStyles.Body,
            color = colors.tertiary,
        )

        Spacer(Modifier.height(dimensions.spaces.x8))

        AppOtpField(
            value = state.otp,
            onValueChange = { event(OtpUiEvent.OtpChanged(it)) },
            isError = state.otpError != null,
            modifier = Modifier.fillMaxWidth(),
        )

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
                label = "New password",
                isRequired = true,
                placeholder = "Enter your new password",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                isError = state.newPasswordError != null,
                errorMessage = state.newPasswordError,
            )

            Spacer(Modifier.height(dimensions.spaces.x4))

            AppTextField(
                value = state.confirmPassword,
                onValueChange = { event(OtpUiEvent.ConfirmPasswordChanged(it)) },
                label = "Confirm new password",
                isRequired = true,
                placeholder = "Re-enter your new password",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                isError = state.confirmPasswordError != null,
                errorMessage = state.confirmPasswordError,
            )
        }

        Spacer(Modifier.height(dimensions.spaces.x8))

        AppButton(
            text = if (state.purpose == OtpPurpose.ForgotPassword) "Reset Password" else "Verify",
            onClick = { event(OtpUiEvent.VerifyClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.otp.length == 6 && !state.isLoading,
        )

        Spacer(Modifier.height(dimensions.spaces.x4))


        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (state.canResend) "Didn't receive the code? " else "Resend code in ${state.resendTimer}s  ",
                style = AppTextStyles.Body,
                color = colors.tertiary,
            )
            if (state.canResend) {
                ActionButton(
                    text = "Resend",
                    onClick = { event(OtpUiEvent.ResendClicked) },
                    style = AppTextStyles.BodyMedium,
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
