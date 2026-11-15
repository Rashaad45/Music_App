package com.rotor.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.rotor.core.designsystem.preview.RotorPaletteProvider
import com.rotor.core.designsystem.preview.RotorPreviewSurface
import com.rotor.core.designsystem.theme.RotorPalette
import com.rotor.core.designsystem.theme.RotorTheme

/** A full-width list of menu rows with exactly one focused row. */
@Composable
fun MenuList(
    items: List<MenuItemUi>,
    focusedIndex: Int,
    onItemClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier.background(RotorTheme.colors.background)) {
        itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
            MenuRow(
                label = item.label,
                secondary = item.secondary,
                hasChildren = item.hasChildren,
                focused = index == focusedIndex,
                onClick = { onItemClick(index) },
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 320)
@Composable
private fun MenuListPreview(
    @PreviewParameter(RotorPaletteProvider::class) palette: RotorPalette,
) {
    RotorPreviewSurface(palette) {
        MenuList(
            items =
                listOf(
                    MenuItemUi("music", "Music", hasChildren = true),
                    MenuItemUi("search", "Search"),
                    MenuItemUi("shuffle", "Shuffle All"),
                    MenuItemUi("settings", "Settings", hasChildren = true),
                ),
            focusedIndex = 0,
            onItemClick = {},
        )
    }
}
