package com.rotor.core.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalRotorColors = staticCompositionLocalOf { RotorPalette.Paper.colors }
private val LocalRotorTypography = staticCompositionLocalOf { DefaultRotorTypography }

/** Access point for theme tokens: `RotorTheme.colors.text`, `RotorTheme.typography.rowLabel`. */
object RotorTheme {
    val colors: RotorColors
        @Composable @ReadOnlyComposable
        get() = LocalRotorColors.current

    val typography: RotorTypography
        @Composable @ReadOnlyComposable
        get() = LocalRotorTypography.current
}

/**
 * Provides Rotor's tokens, and maps them onto Material 3 so any foundation component that is used
 * (sheets, dialogs, text fields) picks up the same colours.
 */
@Composable
fun RotorTheme(
    palette: RotorPalette,
    typography: RotorTypography = DefaultRotorTypography,
    content: @Composable () -> Unit,
) {
    val colors = palette.colors
    CompositionLocalProvider(
        LocalRotorColors provides colors,
        LocalRotorTypography provides typography,
    ) {
        MaterialTheme(colorScheme = colors.toMaterialScheme(), content = content)
    }
}

private fun RotorColors.toMaterialScheme(): ColorScheme {
    val base = if (isDark) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = accent,
        onPrimary = background,
        secondary = accent,
        onSecondary = background,
        background = background,
        onBackground = text,
        surface = surface,
        onSurface = text,
        surfaceVariant = surface,
        onSurfaceVariant = secondaryText,
        surfaceContainer = surface,
        surfaceContainerLow = surface,
        surfaceContainerHigh = surface,
        inverseSurface = highlightBackground,
        inverseOnSurface = highlightForeground,
        outline = secondaryText,
    )
}
