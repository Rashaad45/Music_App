package com.rotor.core.designsystem.theme

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RotorPaletteTest {
    /** WCAG AA for normal-size text. */
    private val minContrast = 4.5f

    @Test
    fun `there are exactly four themes`() {
        assertEquals(
            listOf("Paper", "Graphite", "Oled", "Amber"),
            RotorPalette.entries.map { it.name },
        )
    }

    @Test
    fun `body text meets AA contrast on background in every theme`() {
        RotorPalette.entries.forEach { palette ->
            val c = palette.colors
            assertContrast(palette, "text/background", contrastRatio(c.text, c.background))
            assertContrast(palette, "secondary/background", contrastRatio(c.secondaryText, c.background))
            assertContrast(palette, "text/surface", contrastRatio(c.text, c.surface))
        }
    }

    @Test
    fun `highlighted row meets AA contrast in every theme`() {
        RotorPalette.entries.forEach { palette ->
            val c = palette.colors
            assertContrast(palette, "highlight", contrastRatio(c.highlightForeground, c.highlightBackground))
        }
    }

    @Test
    fun `only Paper is a light theme`() {
        assertFalse(RotorPalette.Paper.colors.isDark)
        assertTrue(RotorPalette.Graphite.colors.isDark)
        assertTrue(RotorPalette.Oled.colors.isDark)
        assertTrue(RotorPalette.Amber.colors.isDark)
    }

    @Test
    fun `follow system picks Paper or Graphite`() {
        assertEquals(RotorPalette.Paper, ThemePreference.FollowSystem.resolve(systemInDarkTheme = false))
        assertEquals(RotorPalette.Graphite, ThemePreference.FollowSystem.resolve(systemInDarkTheme = true))
    }

    @Test
    fun `explicit preference ignores system setting`() {
        assertEquals(RotorPalette.Amber, ThemePreference.Amber.resolve(systemInDarkTheme = false))
        assertEquals(RotorPalette.Oled, ThemePreference.Oled.resolve(systemInDarkTheme = false))
        assertEquals(RotorPalette.Paper, ThemePreference.Paper.resolve(systemInDarkTheme = true))
        assertEquals(RotorPalette.Graphite, ThemePreference.Graphite.resolve(systemInDarkTheme = false))
    }

    @Test
    fun `contrast ratio spans black to white`() {
        val ratio =
            contrastRatio(
                RotorPalette.Oled.colors.background,
                androidx.compose.ui.graphics.Color.White,
            )
        assertEquals(21f, ratio, 0.01f)
    }

    private fun assertContrast(
        palette: RotorPalette,
        pair: String,
        ratio: Float,
    ) {
        assertTrue("$palette $pair contrast $ratio < $minContrast", ratio >= minContrast)
    }
}
