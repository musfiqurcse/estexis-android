package com.estexis.core.ui.error

import com.estexis.core.ui.util.UiText

interface ErrorMapper {
    fun map(code: String?): UiText
}
