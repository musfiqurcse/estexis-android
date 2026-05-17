package com.extexis.core.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.extexis.core.android.OnBoardingState
import com.extexis.core.android.presentation.home.homeNavGraph
import com.extexis.core.navigation.HomeRoute
import com.extexis.core.navigation.LoginRoute
import com.extexis.core.navigation.SplashScreenRoute
import com.extexis.forgotpassword.ui.forgotPasswordNavGraph
import com.extexis.login.ui.loginNavGraph
import com.extexis.otp.ui.otpNavGraph
import com.extexis.registration.ui.registrationNavGraph
import com.extexis.welcome.ui.welcomeScreenNavGraph

@Composable
fun AppNavigation(
    appNavState: AppNavState,
    onboardingState: OnBoardingState,
) {
    val startDestination = when (onboardingState) {
        OnBoardingState.Loading -> return
        OnBoardingState.FirstLaunch -> SplashScreenRoute
        OnBoardingState.LoggedOut -> LoginRoute
        OnBoardingState.LoggedIn -> HomeRoute
    }

//    LaunchedEffect(authState) {
//        when(authState) {
//            AuthState.Authenticated -> {
//                appNavState.navHostController.navigate(WelcomeScreenRoute) {
//                    popUpTo(0) { inclusive = true  }
//                }
//            }
//            AuthState.NotAuthenticated -> {
//                appNavState.navHostController.navigate(AccessChoiceNavRoute) {
//                    popUpTo(0) { inclusive = true  }
//                }
//            }
//        }
//    }

    val navController = appNavState.navHostController

    NavHost(navController = navController, startDestination = startDestination) {
        welcomeScreenNavGraph(navController)
        loginNavGraph(navController)
        registrationNavGraph(navController)
        forgotPasswordNavGraph(navController)
        otpNavGraph(navController)
        homeNavGraph()
    }
}
