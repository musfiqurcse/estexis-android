package com.estexis.verifyexistinguser.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.estexis.core.navigation.ExistingUserVerificationRoute
import com.estexis.core.navigation.OtpRoute
import com.estexis.core.common.ApiResult
import com.estexis.core.presentation.BaseViewModel
import com.estexis.core.presentation.UiMessageEvent
import com.estexis.core.ui.error.AppErrorMapper
import com.estexis.core.ui.util.UiText
import com.estexis.core.ui.util.validation.ValidateEmailUseCase
import com.estexis.verifyexistinguser.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExistingUserVerificationViewModel @Inject constructor(
    //private val sendOtpUseCase: SendOtpForForgotPasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val route = savedStateHandle.toRoute<ExistingUserVerificationRoute>()

    private val _state = MutableStateFlow(ExistingUserVerificationState(
        email = route.email
    ))
    val state: StateFlow<ExistingUserVerificationState> = _state

    private val _navigationEvent = Channel<ExistingUserVerificationNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: ExistingUserVerificationUiEvent) {
        when (event) {
            is ExistingUserVerificationUiEvent.EmailChanged ->
                _state.update { it.copy(email = event.email, emailError = null) }

            ExistingUserVerificationUiEvent.SubmitClicked -> submit()

            ExistingUserVerificationUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(ExistingUserVerificationNavigationEvent.Back) }

            is ExistingUserVerificationUiEvent.LastNameChanged ->
                _state.update { it.copy(email = event.lastName, emailError = null) }
        }
    }

    private fun submit() {

        val current = _state.value

        if (current.email.isBlank()) {
            _state.update { it.copy(emailError = UiText.StringResource(R.string.verify_existing_user_screen_email_is_required)) }
            return
        }

        viewModelScope.launch {

            if (!isValidInput()) return@launch

            _state.update { it.copy(isLoading = true) }
//            when (val result = sendOtpUseCase.sendOtp(current.email, current.lastName)) {
//                is ApiResult.Success -> {
//                    _state.update { it.copy(isLoading = false) }
//                    _navigationEvent.send(
//                        ForgotPasswordNavigationEvent.ToOtp(
//                            email = current.email,
//                            lastName = current.lastName
//                        )
//                    )
//                }
//                is ApiResult.Error -> {
//                    sendMessage(
//                        UiMessageEvent.ToastMessage(
//                            AppErrorMapper.map(result.code)
//                        )
//                    )
//                    _state.update { it.copy(isLoading = false) }
//                }
//            }
        }
    }

    private fun isValidInput(): Boolean {
        val isValidEmail = isValidEmail()
        val isValidLastName = isValidLastName()
        return isValidEmail && isValidLastName
    }

    private fun isValidEmail(): Boolean {
        val result = validateEmailUseCase.validate(_state.value.email)
        _state.update { it.copy(emailError = result.errorMessage) }
        return result.isSuccessful
    }

    private fun isValidLastName(): Boolean {
        if (_state.value.lastName.isBlank()) {
            _state.update { it.copy(lastNameError = UiText.StringResource(R.string.verify_existing_user_screen_last_name_is_required)) }
            return false
        }
        return true
    }
}
