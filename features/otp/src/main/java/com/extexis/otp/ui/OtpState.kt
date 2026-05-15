package com.extexis.otp.ui

import com.extexis.core.navigation.OtpPurpose

data class OtpState(
    val otp: String = "",
    val email: String = "",
    val purpose: OtpPurpose = OtpPurpose.REGISTRATION,
    val isLoading: Boolean = false,
    val otpError: String? = null,
    val resendTimer: Int = 60,
    val canResend: Boolean = false,
    val newPassword: String = "",
    val confirmPassword: String = "",
    val newPasswordError: String? = null,
    val confirmPasswordError: String? = null,
)
