package com.extexis.core.ui.error

import com.extexis.core.ui.R
import com.extexis.core.ui.util.UiText

object AppErrorMapper : ErrorMapper {
    override fun map(code: String?): UiText = when (code) {
        "NO_INTERNET" -> UiText.StringResource(R.string.error_message_no_internet)
        // "E_2001"      -> UiText.StringResource(R.string.error_message_unauthorized)
        // "E_2999"      -> UiText.StringResource(R.string.error_message_server_error)
        else -> UiText.StringResource(R.string.error_message_something_went_wrong)
    }
}
