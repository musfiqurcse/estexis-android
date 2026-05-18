package com.estexis.otp.ui

import com.estexis.core.navigation.OtpPurpose
import com.estexis.core.ui.util.UiText

data class OtpState(
    val otp: String = "",
    val email: String = "",
    val lastName: String = "",
    val purpose: OtpPurpose = OtpPurpose.REGISTRATION,
    val isLoading: Boolean = false,
    val otpError: UiText? = null,
    val resendTimer: Int = 60,
    val canResend: Boolean = false,
    val newPassword: String = "",
    val confirmPassword: String = "",
    val newPasswordError: UiText? = null,
    val confirmPasswordError: UiText? = null,
)
