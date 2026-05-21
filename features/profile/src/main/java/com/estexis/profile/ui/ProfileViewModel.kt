package com.estexis.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val _navigationEvent = Channel<ProfileNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            ProfileUiEvent.BackClicked ->
                viewModelScope.launch { _navigationEvent.send(ProfileNavigationEvent.Back) }
            ProfileUiEvent.KycClicked ->
                viewModelScope.launch { _navigationEvent.send(ProfileNavigationEvent.ToKyc) }
            ProfileUiEvent.AccountInformationClicked,
            ProfileUiEvent.LanguageClicked,
            ProfileUiEvent.SecurityClicked,
            ProfileUiEvent.NotificationClicked,
            ProfileUiEvent.TermsClicked,
            ProfileUiEvent.PrivacyPolicyClicked -> Unit
        }
    }
}
