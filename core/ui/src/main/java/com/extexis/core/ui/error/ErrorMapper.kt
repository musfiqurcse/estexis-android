package com.extexis.core.ui.error

import com.extexis.core.ui.util.UiText

interface ErrorMapper {
    fun map(code: String?): UiText
}
