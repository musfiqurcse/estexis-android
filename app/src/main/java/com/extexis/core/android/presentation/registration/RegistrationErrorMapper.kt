package com.extexis.core.android.presentation.registration

import com.extexis.core.android.R
import com.extexis.core.android.util.UiText


enum class RegistrationErrorCode(val raw: String) {
    VALIDATION_FAILED("VALIDATION_FAILED"),
    USER_EMAIL_EXISTS("USER_EMAIL_EXISTS"),
    AUTH_RATE_LIMITED("AUTH_RATE_LIMITED"),
    NO_INTERNET("NO_INTERNET"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun from(raw: String?): RegistrationErrorCode =
            entries.find { it.raw == raw } ?: UNKNOWN
    }
}

object RegistrationErrorMapper {
    fun toUiMessage(code: RegistrationErrorCode): UiText {
        return when (code) {
            RegistrationErrorCode.VALIDATION_FAILED ->
                UiText.StringResource(R.string.error_message_invalid_credentials)

            RegistrationErrorCode.USER_EMAIL_EXISTS ->
                UiText.StringResource(R.string.error_message_email_already_exists)

            RegistrationErrorCode.AUTH_RATE_LIMITED ->
                UiText.StringResource(R.string.error_message_too_many_attempts)

            RegistrationErrorCode.NO_INTERNET ->
                UiText.StringResource(R.string.error_message_no_internet)

            RegistrationErrorCode.UNKNOWN ->
                UiText.StringResource(R.string.error_message_something_went_wrong)
        }
    }
}
