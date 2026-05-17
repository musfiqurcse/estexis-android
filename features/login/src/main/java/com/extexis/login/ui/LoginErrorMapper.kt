package com.extexis.login.ui

import com.extexis.core.ui.error.AppErrorMapper
import com.extexis.core.ui.error.ErrorMapper
import com.extexis.core.ui.util.UiText
import com.extexis.login.R

object LoginErrorMapper : ErrorMapper {

    const val NOT_VERIFIED = "E_2001"

    override fun map(code: String?): UiText = when (code) {
        NOT_VERIFIED -> UiText.StringResource(R.string.error_message_account_not_verified)
        else -> AppErrorMapper.map(code)
    }
}
