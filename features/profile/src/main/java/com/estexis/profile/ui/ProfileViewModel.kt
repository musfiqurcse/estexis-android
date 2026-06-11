package com.estexis.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estexis.core.common.ApiResult
import com.estexis.profile.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _navigationEvent = Channel<ProfileNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            ProfileUiEvent.KycClicked ->
                viewModelScope.launch { _navigationEvent.send(ProfileNavigationEvent.ToKyc) }

            ProfileUiEvent.LogoutClicked ->
                _state.update { it.copy(showLogoutDialog = true) }

            ProfileUiEvent.DismissLogoutDialog ->
                _state.update { it.copy(showLogoutDialog = false, logoutError = null) }

            ProfileUiEvent.ConfirmLogout -> logout()

            ProfileUiEvent.AccountInformationClicked,
            ProfileUiEvent.LanguageClicked,
            ProfileUiEvent.SecurityClicked,
            ProfileUiEvent.NotificationClicked,
            ProfileUiEvent.TermsClicked,
            ProfileUiEvent.PrivacyPolicyClicked -> Unit
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(isLoggingOut = true, logoutError = null) }
            when (val result = logoutUseCase.invoke()) {
                is ApiResult.Success ->
                    _navigationEvent.send(ProfileNavigationEvent.ToLogin)
                is ApiResult.Error ->
                    _state.update { it.copy(isLoggingOut = false, logoutError = result.message ?: "Logout failed. Please try again.") }
            }
        }
    }
}
