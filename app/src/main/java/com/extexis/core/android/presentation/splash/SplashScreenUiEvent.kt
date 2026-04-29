package com.extexis.core.android.presentation.splash

sealed class SplashScreenUiEvent {

    object GetStarted : SplashScreenUiEvent()

}

sealed class SplashScreenNavigationEvent {

    data object ToLogin : SplashScreenNavigationEvent()

}
