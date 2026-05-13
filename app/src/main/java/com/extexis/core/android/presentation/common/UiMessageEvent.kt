package com.extexis.core.android.presentation.common

import com.extexis.core.android.util.UiText


sealed class UiMessageEvent() {

    data class SnackBarMessage(val errorMessage: UiText? = null): UiMessageEvent()

    data class ToastMessage(val errorMessage: UiText? = null): UiMessageEvent()

}
