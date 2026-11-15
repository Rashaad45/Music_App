package com.rotor.core.designsystem.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.rotor.core.designsystem.theme.RotorPalette
import com.rotor.core.designsystem.theme.RotorTheme

/** Feeds every built-in theme into a `@PreviewParameter`, so each preview renders once per theme. */
class RotorPaletteProvider : PreviewParameterProvider<RotorPalette> {
    override val values: Sequence<RotorPalette> = RotorPalette.entries.asSequence()

    override fun getDisplayName(index: Int): String = RotorPalette.entries[index].name
}

/** Wraps preview content in the given theme on its background colour. */
@Composable
fun RotorPreviewSurface(
    palette: RotorPalette,
    content: @Composable () -> Unit,
) {
    RotorTheme(palette) {
        Box(Modifier.background(RotorTheme.colors.background)) { content() }
    }
}
