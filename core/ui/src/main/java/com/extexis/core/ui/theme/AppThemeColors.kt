package com.extexis.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private object LightPalette {
    val Primary          = Color(0xFFEA5B1B)
    val OnPrimary        = Color(0xFFF7F3ED)
    val PrimaryContainer = Color(0xFFF9CEAF)
    val Secondary        = Color(0xFF1F7C5E)
    val OnSecondary      = Color(0xFFEFFAF5)
    val SecondaryContainer = Color(0xFFB4E6CE)
    val Tertiary         = Color(0xFF4D4D4F)
    val OnTertiary       = Color(0xFF636364)
    val TertiaryContainer = Color(0xFFC9ECC6)
    val Background       = Color(0xFFFFFFFF)
    val OnBackground     = Color(0xFF121212)
    val Surface          = Color(0xFFF5F5F5)
    val SurfaceDim       = Color(0xFFB4B4B4)
    val Action           = Color(0xFF00A7E4)
    val Error            = Color(0xFFC40505)
    val OnError          = Color(0xFFFFFFFF)
    val Success          = Color(0xFF029902)
    val Border           = Color(0xFFE1E3E6)
    val White            = Color(0xFFFFFFFF)
}

private object DarkPalette {
    val Primary          = Color(0xFFEA5B1B)
    val OnPrimary        = Color(0xFF3A1508)
    val PrimaryContainer = Color(0xFF5C2410)
    val Secondary        = Color(0xFF2AB17F)
    val OnSecondary      = Color(0xFF0C3326)
    val SecondaryContainer = Color(0xFF1A5240)
    val Tertiary         = Color(0xFF9E9EA0)
    val OnTertiary       = Color(0xFF2E2E30)
    val TertiaryContainer = Color(0xFF2A3828)
    val Background       = Color(0xFF111111)
    val OnBackground     = Color(0xFFF0EEEB)
    val Surface          = Color(0xFF1C1C1C)
    val SurfaceDim       = Color(0xFF3A3A3A)
    val Action           = Color(0xFF33B8F5)
    val Error            = Color(0xFFFF5252)
    val OnError          = Color(0xFF5A0000)
    val Success          = Color(0xFF4CAF50)
    val Border           = Color(0xFF2A2A2A)
    val White            = Color(0xFFFFFFFF)
}

interface AppColorsContract {
    val primary: Color
    val onPrimary: Color
    val primaryContainer: Color
    val secondary: Color
    val onSecondary: Color
    val secondaryContainer: Color
    val tertiary: Color
    val onTertiary: Color
    val tertiaryContainer: Color
    val background: Color
    val onBackground: Color
    val surface: Color
    val surfaceDim: Color
    val action: Color
    val error: Color
    val onError: Color
    val success: Color
    val border: Color
    val white: Color
}

object ExtexisColorsLight : AppColorsContract {
    override val primary           = LightPalette.Primary
    override val onPrimary         = LightPalette.OnPrimary
    override val primaryContainer  = LightPalette.PrimaryContainer
    override val secondary         = LightPalette.Secondary
    override val onSecondary       = LightPalette.OnSecondary
    override val secondaryContainer = LightPalette.SecondaryContainer
    override val tertiary          = LightPalette.Tertiary
    override val onTertiary        = LightPalette.OnTertiary
    override val tertiaryContainer = LightPalette.TertiaryContainer
    override val background        = LightPalette.Background
    override val onBackground      = LightPalette.OnBackground
    override val surface           = LightPalette.Surface
    override val surfaceDim        = LightPalette.SurfaceDim
    override val action            = LightPalette.Action
    override val error             = LightPalette.Error
    override val onError           = LightPalette.OnError
    override val success           = LightPalette.Success
    override val border            = LightPalette.Border
    override val white             = LightPalette.White
}

object ExtexisColorsDark : AppColorsContract {
    override val primary           = DarkPalette.Primary
    override val onPrimary         = DarkPalette.OnPrimary
    override val primaryContainer  = DarkPalette.PrimaryContainer
    override val secondary         = DarkPalette.Secondary
    override val onSecondary       = DarkPalette.OnSecondary
    override val secondaryContainer = DarkPalette.SecondaryContainer
    override val tertiary          = DarkPalette.Tertiary
    override val onTertiary        = DarkPalette.OnTertiary
    override val tertiaryContainer = DarkPalette.TertiaryContainer
    override val background        = DarkPalette.Background
    override val onBackground      = DarkPalette.OnBackground
    override val surface           = DarkPalette.Surface
    override val surfaceDim        = DarkPalette.SurfaceDim
    override val action            = DarkPalette.Action
    override val error             = DarkPalette.Error
    override val onError           = DarkPalette.OnError
    override val success           = DarkPalette.Success
    override val border            = DarkPalette.Border
    override val white             = DarkPalette.White
}

class ExtexisColors(isDark: Boolean) : AppColorsContract
    by if (isDark) ExtexisColorsDark else ExtexisColorsLight

fun AppColorsContract.toMaterialColorScheme(isDark: Boolean): ColorScheme =
    if (isDark)
        darkColorScheme(
            primary          = primary,
            onPrimary        = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = primary,
            secondary        = secondary,
            onSecondary      = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = secondary,
            tertiary         = tertiary,
            onTertiary       = onTertiary,
            tertiaryContainer = tertiaryContainer,
            background       = background,
            onBackground     = onBackground,
            surface          = surface,
            onSurface        = onBackground,
            surfaceVariant   = surfaceDim,
            onSurfaceVariant = tertiary,
            outline          = border,
            error            = error,
            onError          = onError,
        )
    else
        lightColorScheme(
            primary          = primary,
            onPrimary        = onPrimary,
            primaryContainer = primaryContainer,
            onPrimaryContainer = primary,
            secondary        = secondary,
            onSecondary      = onSecondary,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = secondary,
            tertiary         = tertiary,
            onTertiary       = onTertiary,
            tertiaryContainer = tertiaryContainer,
            background       = background,
            onBackground     = onBackground,
            surface          = surface,
            onSurface        = onBackground,
            surfaceVariant   = surfaceDim,
            onSurfaceVariant = tertiary,
            outline          = border,
            error            = error,
            onError          = onError,
        )
