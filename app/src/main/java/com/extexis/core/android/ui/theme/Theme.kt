package com.extexis.core.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.extexis.core.android.R


val DMSans = FontFamily(
    Font(R.font.dm_sans_regular, FontWeight.Normal),
    Font(R.font.dm_sans_medium, FontWeight.Medium),
    Font(R.font.dm_sans_medium, FontWeight.SemiBold),
    Font(R.font.dm_sans_medium, FontWeight.Bold),
)

private val AppMaterialTypography = Typography(
    displayLarge = AppTextStyles.Hero,
    displayMedium = AppTextStyles.Title,
    headlineLarge = AppTextStyles.SectionHeading,
    titleLarge = AppTextStyles.SectionHeading,
    titleMedium = AppTextStyles.CardTitle,
    bodyLarge = AppTextStyles.BodyLarge,
    bodyMedium = AppTextStyles.Body,
    bodySmall = AppTextStyles.Meta,
    labelLarge = AppTextStyles.ButtonLabel,
    labelMedium = AppTextStyles.BodyMedium,
    labelSmall = AppTextStyles.Caption,
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

    MaterialTheme(
        colorScheme = colors.toMaterialColorScheme(isDark = darkTheme),
        typography = AppMaterialTypography,
    ) {
        CompositionLocalProvider(LocalAppColors provides colors) {
            content()
        }
    }
}