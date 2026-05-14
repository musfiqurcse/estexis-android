package com.extexis.splash.ui

sealed class SplashScreenUiEvent {

    object GetStarted : SplashScreenUiEvent()

}

sealed class SplashScreenNavigationEvent {

    data object ToLogin : SplashScreenNavigationEvent()

}
