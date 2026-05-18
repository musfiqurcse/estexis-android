package com.estexis.welcome.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estexis.core.datastore.AppOnBoardingPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val onBoardingPreference: AppOnBoardingPreference,
) : ViewModel() {

    private val _navigationEvent = Channel<WelcomeScreenNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: WelcomeScreenUiEvent) {
        when (event) {
            WelcomeScreenUiEvent.GetStarted -> handleGetStarted()
        }
    }

    private fun handleGetStarted() {
        viewModelScope.launch {
            onBoardingPreference.set(true)
            _navigationEvent.send(WelcomeScreenNavigationEvent.ToLogin)
        }
    }
}
