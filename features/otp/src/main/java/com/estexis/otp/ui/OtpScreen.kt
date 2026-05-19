package com.estexis.otp.ui

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
import com.estexis.core.navigation.OtpPurpose
import com.estexis.core.ui.gds.ActionButton
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppIconButton
import com.estexis.core.ui.gds.AppLoadingDialog
import com.estexis.core.ui.gds.AppOtpField
import com.estexis.core.ui.gds.AppTextField
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.otp.R

@Composable
fun OtpScreen(
    state: OtpState,
    event: (OtpUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    if (state.isLoading) {
        AppLoadingDialog()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = dimensions.spaces.x4)
    ) {
        VerticalSpacer(dimensions.spaces.x4)

        AppIconButton(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { event(OtpUiEvent.BackClicked) },
            contentDescription = "Back",
        )

        VerticalSpacer(dimensions.spaces.x6)

        Text(
            text = stringResource(R.string.otp_screen_title_verification_code),
            style = AppTheme.typography.H2SemiBold,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x2)

        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.otp_screen_message_otp_sent))
                withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = colors.action)) {
                    append(state.email)
                }
            },
            style = AppTextStyles.BodyText3Regular,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x8)

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

        state.otpError?.let { error ->
            Spacer(Modifier.height(dimensions.spaces.x2))
            Text(
                text = error.asString(),
                style = AppTextStyles.BodyText2Regular,
                color = colors.error,
            )
        }

        VerticalSpacer(dimensions.spaces.x6)

        if (state.purpose == OtpPurpose.FORGOT_PASSWORD) {
            Spacer(Modifier.height(dimensions.spaces.x6))

            AppTextField(
                value = state.newPassword,
                onValueChange = { event(OtpUiEvent.NewPasswordChanged(it)) },
                label = stringResource(R.string.otp_screen_label_new_password),
                isRequired = true,
                placeholder = stringResource(R.string.otp_screen_placeholder_enter_your_new_password),
                isPassword = true,
                isError = state.newPasswordError != null,
                errorMessage = state.newPasswordError?.asString(),
            )

            VerticalSpacer(dimensions.spaces.x4)

            AppTextField(
                value = state.confirmPassword,
                onValueChange = { event(OtpUiEvent.ConfirmPasswordChanged(it)) },
                label = stringResource(R.string.otp_screen_confirm_new_password),
                isRequired = true,
                placeholder = stringResource(R.string.otp_screen_placeholder_re_enter_your_new_password),
                isPassword = true,
                isError = state.confirmPasswordError != null,
                errorMessage = state.confirmPasswordError?.asString(),
            )
        }

        VerticalSpacer(dimensions.spaces.x8)

        AppButton(
            text = if (state.purpose == OtpPurpose.FORGOT_PASSWORD)
                stringResource(R.string.otp_screen_cta_reset_password)
            else stringResource(R.string.otp_screen_cta_verify),
            onClick = { event(OtpUiEvent.VerifyClicked) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.otp.length == 6 && !state.isLoading,
        )

        VerticalSpacer(dimensions.spaces.x4)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (state.canResend)
                    stringResource(R.string.otp_screen_didn_t_receive_the_code)
                else stringResource(R.string.otp_screen_resend_code_in_s, state.resendTimer),
                style = AppTheme.typography.BodyText3Regular,
                color = colors.onBackground,
            )
            if (state.canResend) {
                ActionButton(
                    text = stringResource(R.string.otp_screen_cta_resend),
                    onClick = { event(OtpUiEvent.ResendClicked) },
                    style = AppTheme.typography.BodyText3Bold,
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
            state = OtpState(email = "dd@gmail.com", purpose = OtpPurpose.REGISTRATION),
            event = {},
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun OtpScreenVerifyExistingUserPreview() {
    ExtexisAndroidTheme {
        OtpScreen(
            state = OtpState(email = "dd@gmail.com", purpose = OtpPurpose.VERIFY_EXISTING_USER),
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
                purpose = OtpPurpose.FORGOT_PASSWORD,
                otp = "123456",
                canResend = true,
            ),
            event = {},
        )
    }
}
