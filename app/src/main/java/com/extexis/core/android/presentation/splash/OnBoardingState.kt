package com.extexis.core.android.presentation.splash

sealed class OnBoardingState {

    data object Loading : OnBoardingState()

    data object FirstLaunch : OnBoardingState()

    data object LoggedOut : OnBoardingState()

    data object LoggedIn : OnBoardingState()
}
