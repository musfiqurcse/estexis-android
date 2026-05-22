package com.estexis.core.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.estexis.core.android.OnBoardingState
import com.estexis.core.navigation.HomeRoute
import com.estexis.core.navigation.LoginRoute
import com.estexis.core.navigation.PassportVerificationRoute
import com.estexis.core.navigation.SplashScreenRoute
import com.estexis.forgotpassword.ui.forgotPasswordNavGraph
import com.estexis.home.ui.homeNavGraph
import com.estexis.kyc.ui.kycNavGraph
import com.estexis.login.ui.loginNavGraph
import com.estexis.otp.ui.otpNavGraph
import com.estexis.profile.ui.profileNavGraph
import com.estexis.registration.ui.registrationNavGraph
import com.estexis.verifyexistinguser.ui.existingUserVerificationNavGraph
import com.estexis.welcome.ui.welcomeScreenNavGraph

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

    NavHost(navController = navController, startDestination = PassportVerificationRoute) {
        welcomeScreenNavGraph(navController)
        loginNavGraph(navController)
        existingUserVerificationNavGraph(navController)
        registrationNavGraph(navController)
        forgotPasswordNavGraph(navController)
        otpNavGraph(navController)
        homeNavGraph(navController)
        profileNavGraph(navController)
        kycNavGraph(navController)
    }
}
