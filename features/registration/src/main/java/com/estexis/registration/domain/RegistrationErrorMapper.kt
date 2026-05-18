package com.estexis.registration.domain

import com.estexis.core.ui.R
import com.estexis.core.ui.error.AppErrorMapper
import com.estexis.core.ui.error.ErrorMapper
import com.estexis.core.ui.util.UiText

enum class RegistrationErrorCode(val raw: String) {
    VALIDATION_ERROR("E_1008"),
    EMAIL_CONFLICT("E_2004"),
    RATE_LIMITED("E_2005"),
    NO_INTERNET("NO_INTERNET"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun from(raw: String?): RegistrationErrorCode =
            entries.find { it.raw == raw } ?: UNKNOWN
    }
}

object RegistrationErrorMapper : ErrorMapper {

    override fun map(code: String?): UiText = when (code) {
        "E_2004" -> UiText.StringResource(R.string.error_message_email_already_exists)
        else -> AppErrorMapper.map(code)
    }

//    fun toUiMessage(code: RegistrationErrorCode): UiText {
//        return when (code) {
//            RegistrationErrorCode.VALIDATION_ERROR ->
//                UiText.StringResource(R.string.error_message_invalid_credentials)
//
//            RegistrationErrorCode.EMAIL_CONFLICT ->
//                UiText.StringResource(R.string.error_message_email_already_exists)
//
//            RegistrationErrorCode.RATE_LIMITED ->
//                UiText.StringResource(R.string.error_message_too_many_attempts)
//
//            RegistrationErrorCode.NO_INTERNET ->
//                UiText.StringResource(R.string.error_message_no_internet)
//
//            RegistrationErrorCode.UNKNOWN ->
//                UiText.StringResource(R.string.error_message_something_went_wrong)
//        }
//    }
}
