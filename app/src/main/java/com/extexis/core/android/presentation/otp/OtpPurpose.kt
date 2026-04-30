package com.extexis.core.android.presentation.otp

enum class OtpPurpose {
    Registration,
    ForgotPassword;

    companion object {
        fun fromString(value: String): OtpPurpose =
            entries.firstOrNull { it.name == value } ?: Registration
    }
}
