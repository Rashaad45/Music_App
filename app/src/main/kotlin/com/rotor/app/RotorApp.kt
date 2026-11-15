package com.rotor.app

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rotor.core.designsystem.theme.RotorTheme
import com.rotor.core.designsystem.theme.ThemePreference
import com.rotor.core.designsystem.theme.resolve
import com.rotor.feature.menu.MenuRoute

/**
 * App shell and theme host. The theme follows the system and switches live; the user's
 * Settings ▸ Appearance choice replaces [ThemePreference.FollowSystem] once settings exist.
 */
@Composable
fun RotorApp() {
    val palette = ThemePreference.FollowSystem.resolve(isSystemInDarkTheme())
    RotorTheme(palette) {
        Box(
            Modifier
                .fillMaxSize()
                .background(RotorTheme.colors.background)
                .safeDrawingPadding(),
        ) {
            MenuRoute()
        }
    }
}
