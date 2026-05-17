package com.extexis.core.navigation

enum class OtpPurpose {
    REGISTRATION,
    FORGOT_PASSWORD,
    VERIFY_EXISTING_USER;

    companion object {
        fun fromString(value: String): OtpPurpose =
            entries.firstOrNull { it.name == value } ?: REGISTRATION
    }
}
