package com.rotor.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/** The seven colour tokens every Rotor theme defines. */
@Immutable
data class RotorColors(
    val background: Color,
    val surface: Color,
    val text: Color,
    val secondaryText: Color,
    val highlightBackground: Color,
    val highlightForeground: Color,
    val accent: Color,
    val isDark: Boolean,
)

/** The four built-in themes. All colours are original to Rotor. */
enum class RotorPalette(
    val colors: RotorColors,
) {
    /** Warm off-white paper with ink-black text. */
    Paper(
        RotorColors(
            background = Color(0xFFF5F2EB),
            surface = Color(0xFFEAE5DA),
            text = Color(0xFF1C1B19),
            secondaryText = Color(0xFF5F5B53),
            highlightBackground = Color(0xFF1C1B19),
            highlightForeground = Color(0xFFF5F2EB),
            accent = Color(0xFFB4410C),
            isDark = false,
        ),
    ),

    /** Soft charcoal for dark mode. */
    Graphite(
        RotorColors(
            background = Color(0xFF1B1D20),
            surface = Color(0xFF26292D),
            text = Color(0xFFE8E6E3),
            secondaryText = Color(0xFF9EA1A7),
            highlightBackground = Color(0xFFE8E6E3),
            highlightForeground = Color(0xFF1B1D20),
            accent = Color(0xFF8AB8FF),
            isDark = true,
        ),
    ),

    /** Pure black for OLED panels. */
    Oled(
        RotorColors(
            background = Color(0xFF000000),
            surface = Color(0xFF0F0F0F),
            text = Color(0xFFF2F2F2),
            secondaryText = Color(0xFF8E8E8E),
            highlightBackground = Color(0xFFF2F2F2),
            highlightForeground = Color(0xFF000000),
            accent = Color(0xFF5BE38C),
            isDark = true,
        ),
    ),

    /** Retro monochrome amber phosphor. */
    Amber(
        RotorColors(
            background = Color(0xFF120B02),
            surface = Color(0xFF1F1505),
            text = Color(0xFFFFB000),
            secondaryText = Color(0xFFBF8400),
            highlightBackground = Color(0xFFFFB000),
            highlightForeground = Color(0xFF120B02),
            accent = Color(0xFFFFD166),
            isDark = true,
        ),
    ),
}

/** What the user picked in Settings ▸ Appearance ▸ Theme. */
enum class ThemePreference {
    FollowSystem,
    Paper,
    Graphite,
    Oled,
    Amber,
}

/** Follow-system maps to Paper (light) or Graphite (dark); explicit choices win. */
fun ThemePreference.resolve(systemInDarkTheme: Boolean): RotorPalette =
    when (this) {
        ThemePreference.FollowSystem -> if (systemInDarkTheme) RotorPalette.Graphite else RotorPalette.Paper
        ThemePreference.Paper -> RotorPalette.Paper
        ThemePreference.Graphite -> RotorPalette.Graphite
        ThemePreference.Oled -> RotorPalette.Oled
        ThemePreference.Amber -> RotorPalette.Amber
    }
