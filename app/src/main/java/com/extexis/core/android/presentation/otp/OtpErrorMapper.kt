package com.extexis.core.android.presentation.otp

import com.extexis.core.android.R
import com.extexis.core.android.util.UiText


enum class OtpErrorCode(val raw: String) {
    VALIDATION_OTP_INVALID("VALIDATION_OTP_INVALID"),
    USER_NOT_FOUND("USER_NOT_FOUND"),
    NO_INTERNET("NO_INTERNET"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun from(raw: String?): OtpErrorCode =
            entries.find { it.raw == raw } ?: UNKNOWN
    }
}

object OtpErrorMapper {
    fun toUiMessage(code: OtpErrorCode): UiText {
        return when (code) {
            OtpErrorCode.VALIDATION_OTP_INVALID ->
                UiText.StringResource(R.string.error_message_invalid_otp)

            OtpErrorCode.NO_INTERNET ->
                UiText.StringResource(R.string.error_message_no_internet)

            OtpErrorCode.USER_NOT_FOUND ->
                UiText.StringResource(R.string.error_message_email_not_registered)

            OtpErrorCode.UNKNOWN ->
                UiText.StringResource(R.string.error_message_something_went_wrong)
        }
    }
}
