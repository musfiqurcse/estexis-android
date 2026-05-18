package com.estexis.login.ui

import com.estexis.core.ui.error.AppErrorMapper
import com.estexis.core.ui.error.ErrorMapper
import com.estexis.core.ui.util.UiText
import com.estexis.login.R

object LoginErrorMapper : ErrorMapper {

    const val NOT_VERIFIED = "E_2001"

    override fun map(code: String?): UiText = when (code) {
        NOT_VERIFIED -> UiText.StringResource(R.string.error_message_account_not_verified)
        else -> AppErrorMapper.map(code)
    }
}
