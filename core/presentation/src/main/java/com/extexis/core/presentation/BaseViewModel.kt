package com.extexis.core.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel : ViewModel() {

    private val _uiMessageEvent = MutableSharedFlow<UiMessageEvent>()
    val uiMessageEvent = _uiMessageEvent.asSharedFlow()

    protected suspend fun sendMessage(messageEvent: UiMessageEvent) {
        _uiMessageEvent.emit(messageEvent)
    }

}
