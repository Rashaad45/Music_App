package com.rotor.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.rotor.core.designsystem.R
import com.rotor.core.designsystem.preview.RotorPaletteProvider
import com.rotor.core.designsystem.preview.RotorPreviewSurface
import com.rotor.core.designsystem.theme.RotorPalette
import com.rotor.core.designsystem.theme.RotorTheme

/** Minimum row height from the spec (§5). */
val MenuRowMinHeight = 56.dp

/**
 * One menu row: label, optional secondary text, trailing chevron for submenus. A focused row is
 * drawn with inverted colours (the highlight bar).
 */
@Composable
fun MenuRow(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    secondary: String? = null,
    hasChildren: Boolean = false,
    focused: Boolean = false,
) {
    val colors = RotorTheme.colors
    val fg = if (focused) colors.highlightForeground else colors.text
    val fgSecondary = if (focused) colors.highlightForeground else colors.secondaryText
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .heightIn(min = MenuRowMinHeight)
                .background(if (focused) colors.highlightBackground else colors.background)
                .semantics { selected = focused }
                .clickable(role = Role.Button, onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = label,
                style = RotorTheme.typography.rowLabel,
                color = fg,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (secondary != null) {
                Text(
                    text = secondary,
                    style = RotorTheme.typography.rowSecondary,
                    color = fgSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (hasChildren) {
            Icon(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = fg,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Preview(widthDp = 360)
@Composable
private fun MenuRowPreview(
    @PreviewParameter(RotorPaletteProvider::class) palette: RotorPalette,
) {
    RotorPreviewSurface(palette) {
        Column {
            MenuRow(label = "Music", onClick = {}, hasChildren = true, focused = true)
            MenuRow(label = "Night Drive", onClick = {}, secondary = "Lumen Fields")
        }
    }
}
