package com.estexis.twofactorauth.ui

import androidx.lifecycle.viewModelScope
import com.estexis.core.common.ApiResult
import com.estexis.core.presentation.BaseViewModel
import com.estexis.core.presentation.UiMessageEvent
import com.estexis.core.ui.error.AppErrorMapper
import com.estexis.twofactorauth.domain.ToggleTwoFactorAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwoFactorAuthViewModel @Inject constructor(
    private val toggleTwoFactorAuthUseCase: ToggleTwoFactorAuthUseCase,
) : BaseViewModel() {

    private val _state = MutableStateFlow(TwoFactorAuthState())
    val state: StateFlow<TwoFactorAuthState> = _state

    private val _navigationEvent = Channel<TwoFactorAuthNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: TwoFactorAuthUiEvent) {
        when (event) {
            TwoFactorAuthUiEvent.ToggleClicked -> toggle()
            TwoFactorAuthUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(TwoFactorAuthNavigationEvent.Back) }
        }
    }

    private fun toggle() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = if (_state.value.isEnabled) {
                toggleTwoFactorAuthUseCase.disable()
            } else {
                toggleTwoFactorAuthUseCase.enable()
            }
            when (result) {
                is ApiResult.Success ->
                    _state.update { it.copy(isEnabled = !it.isEnabled, isLoading = false) }
                is ApiResult.Error -> {
                    sendMessage(UiMessageEvent.ToastMessage(AppErrorMapper.map(result.code)))
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }
}
