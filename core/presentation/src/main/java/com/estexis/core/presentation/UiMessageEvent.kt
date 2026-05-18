package com.estexis.core.presentation

import com.estexis.core.ui.util.UiText

sealed class UiMessageEvent {

    data class SnackBarMessage(val errorMessage: UiText? = null) : UiMessageEvent()

    data class ToastMessage(val errorMessage: UiText? = null) : UiMessageEvent()
}
