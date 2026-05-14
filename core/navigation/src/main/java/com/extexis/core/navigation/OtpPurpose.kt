package com.extexis.core.navigation

enum class OtpPurpose {
    Registration,
    ForgotPassword;

    companion object {
        fun fromString(value: String): OtpPurpose =
            entries.firstOrNull { it.name == value } ?: Registration
    }
}
