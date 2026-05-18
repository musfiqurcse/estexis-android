package com.estexis.core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import com.estexis.core.ui.R

val MONTSERRAT = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semi_bold, FontWeight.SemiBold),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_extra_bold, FontWeight.ExtraBold),
)

private val AppMaterialTypography = Typography(
    displayLarge = AppTextStyles.H1Bold,
    displayMedium = AppTextStyles.H2Bold,
    displaySmall = AppTextStyles.H3Bold,
    headlineLarge = AppTextStyles.H1Regular,
    headlineMedium = AppTextStyles.H2Regular,
    headlineSmall = AppTextStyles.H3Regular,
    titleLarge = AppTextStyles.BodyText1Bold,
    titleMedium = AppTextStyles.BodyText2Bold,
    titleSmall = AppTextStyles.BodyText3Bold,
    bodyLarge = AppTextStyles.BodyText1Regular,
    bodyMedium = AppTextStyles.BodyText2Regular,
    bodySmall = AppTextStyles.BodyText3Regular,
    labelLarge = AppTextStyles.BodyText2Bold,
    labelMedium = AppTextStyles.BodyText3Bold,
    labelSmall = AppTextStyles.BodyText3Bold,
)

private val appDimensions = AppDimensions()

val LocalAppColors = compositionLocalOf<AppColorsContract> { ExtexisColorsLight }
val LocalSleepDimensions = staticCompositionLocalOf { appDimensions }
val LocalSleepTypography = staticCompositionLocalOf { AppTextStyles }

object AppTheme {
    val colors: AppColorsContract
        @Composable
        get() = LocalAppColors.current

    val dimensions: AppDimensions
        @Composable get() = LocalSleepDimensions.current

    val typography: AppTextStyles
        @Composable get() = LocalSleepTypography.current
}

@Composable
fun ExtexisAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = ExtexisColors(isDark = darkTheme)
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.onPrimary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors.toMaterialColorScheme(isDark = darkTheme),
        typography = AppMaterialTypography,
    ) {
        CompositionLocalProvider(LocalAppColors provides colors) {
            content()
        }
    }
}
