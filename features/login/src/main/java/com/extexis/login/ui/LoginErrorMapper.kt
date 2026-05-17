package com.extexis.login.ui

import com.extexis.core.ui.R
import com.extexis.core.ui.error.AppErrorMapper
import com.extexis.core.ui.error.ErrorMapper
import com.extexis.core.ui.util.UiText

object LoginErrorMapper : ErrorMapper {

    const val NOT_VERIFIED = "E_2002"

    override fun map(code: String?): UiText = when (code) {
        NOT_VERIFIED -> UiText.StringResource(R.string.error_message_email_already_exists)
        else -> AppErrorMapper.map(code)
    }
}
