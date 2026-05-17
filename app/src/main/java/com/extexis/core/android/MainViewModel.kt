package com.extexis.core.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.extexis.core.datastore.AccessTokenPreference
import com.extexis.core.datastore.AppOnBoardingPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onBoardingPreference: AppOnBoardingPreference,
    private val accessTokenPreference: AccessTokenPreference,
) : ViewModel() {

    private val _onboardingState = MutableStateFlow<OnBoardingState>(OnBoardingState.Loading)
    val onboardingState: StateFlow<OnBoardingState> = _onboardingState

    init {
        resolveStartDestination()
    }

    private fun resolveStartDestination() {
        viewModelScope.launch {
            val hasSeenWelcome = onBoardingPreference.get()
            val accessToken = accessTokenPreference.get()

            _onboardingState.value = when {
                !hasSeenWelcome -> OnBoardingState.FirstLaunch
                accessToken.isNotBlank() -> OnBoardingState.LoggedIn
                else -> OnBoardingState.LoggedOut
            }
        }
    }
}
