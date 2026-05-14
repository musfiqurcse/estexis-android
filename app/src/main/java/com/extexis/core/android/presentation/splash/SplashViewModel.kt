package com.extexis.core.android.presentation.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel : ViewModel() {

    private val _onboardingState = MutableStateFlow(OnBoardingState.FirstLaunch)
    val onboardingState: StateFlow<OnBoardingState> = _onboardingState

//    private val _navigationEvent = Channel<SplashScreenNavigationEvent>()
//    val navigationEvent = _navigationEvent.receiveAsFlow()
//
//    fun onEvent(event: SplashScreenUiEvent) {
//        when (event) {
//            SplashScreenUiEvent.GetStarted -> {
//                viewModelScope.launch {
//                    _navigationEvent.send(SplashScreenNavigationEvent.ToLogin)
//                }
//            }
//        }
//    }
}
