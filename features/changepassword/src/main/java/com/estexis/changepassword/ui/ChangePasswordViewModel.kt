package com.estexis.changepassword.ui

import androidx.lifecycle.viewModelScope
import com.estexis.changepassword.R
import com.estexis.changepassword.domain.ChangePasswordUseCase
import com.estexis.core.common.ApiResult
import com.estexis.core.presentation.BaseViewModel
import com.estexis.core.presentation.UiMessageEvent
import com.estexis.core.ui.error.AppErrorMapper
import com.estexis.core.ui.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
) : BaseViewModel() {

    private val _state = MutableStateFlow(ChangePasswordState())
    val state: StateFlow<ChangePasswordState> = _state

    private val _navigationEvent = Channel<ChangePasswordNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: ChangePasswordUiEvent) {
        when (event) {
            is ChangePasswordUiEvent.CurrentPasswordChanged ->
                _state.update { it.copy(currentPassword = event.value, currentPasswordError = null) }

            is ChangePasswordUiEvent.NewPasswordChanged ->
                _state.update { it.copy(newPassword = event.value, newPasswordError = null) }

            is ChangePasswordUiEvent.ConfirmPasswordChanged ->
                _state.update { it.copy(confirmPassword = event.value, confirmPasswordError = null) }

            ChangePasswordUiEvent.SaveClicked -> save()

            ChangePasswordUiEvent.ContinueClicked ->
                viewModelScope.launch { _navigationEvent.send(ChangePasswordNavigationEvent.Back) }

            ChangePasswordUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(ChangePasswordNavigationEvent.Back) }
        }
    }

    private fun save() {
        if (!isValidInput()) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = changePasswordUseCase.invoke(_state.value.currentPassword, _state.value.newPassword)) {
                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                }
                is ApiResult.Error -> {
                    sendMessage(UiMessageEvent.ToastMessage(AppErrorMapper.map(result.code)))
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun isValidInput(): Boolean {
        val s = _state.value
        var valid = true

        if (s.currentPassword.isBlank()) {
            _state.update { it.copy(currentPasswordError = UiText.StringResource(R.string.change_password_screen_old_password_required)) }
            valid = false
        }
        if (s.newPassword.isBlank()) {
            _state.update { it.copy(newPasswordError = UiText.StringResource(R.string.change_password_screen_new_password_required)) }
            valid = false
        }
        if (s.confirmPassword.isBlank()) {
            _state.update { it.copy(confirmPasswordError = UiText.StringResource(R.string.change_password_screen_confirm_password_required)) }
            valid = false
        } else if (s.newPassword != s.confirmPassword) {
            _state.update { it.copy(confirmPasswordError = UiText.StringResource(R.string.change_password_screen_passwords_do_not_match)) }
            valid = false
        }

        return valid
    }
}
