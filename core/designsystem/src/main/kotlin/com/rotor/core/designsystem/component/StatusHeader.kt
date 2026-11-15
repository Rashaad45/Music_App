package com.rotor.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
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

/** The play-state glyph shown at the right of the header. */
enum class PlaybackGlyph { None, Playing, Paused }

/** The connectivity glyph shown at the far right of the header. */
enum class NetworkIndicator { Wifi, Cellular, Offline }

private val HeaderHeight = 36.dp
private val GlyphSize = 16.dp

/** Thin, always-visible header: screen title on the left, playback and network state on the right. */
@Composable
fun StatusHeader(
    title: String,
    modifier: Modifier = Modifier,
    playback: PlaybackGlyph = PlaybackGlyph.None,
    network: NetworkIndicator = NetworkIndicator.Wifi,
) {
    val colors = RotorTheme.colors
    Column(modifier.fillMaxWidth().background(colors.surface)) {
        Row(
            modifier = Modifier.fillMaxWidth().height(HeaderHeight).padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = title,
                style = RotorTheme.typography.header,
                color = colors.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f).semantics { heading() },
            )
            playback.iconAndLabel()?.let { (icon, label) ->
                Icon(
                    painter = painterResource(icon),
                    contentDescription = stringResource(label),
                    tint = colors.text,
                    modifier = Modifier.size(GlyphSize),
                )
            }
            val (netIcon, netLabel) = network.iconAndLabel()
            Icon(
                painter = painterResource(netIcon),
                contentDescription = stringResource(netLabel),
                tint = if (network == NetworkIndicator.Offline) colors.accent else colors.text,
                modifier = Modifier.size(GlyphSize),
            )
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(colors.secondaryText.copy(alpha = 0.25f)))
    }
}

private fun PlaybackGlyph.iconAndLabel(): Pair<Int, Int>? =
    when (this) {
        PlaybackGlyph.None -> null
        PlaybackGlyph.Playing -> R.drawable.ic_play_arrow to R.string.designsystem_state_playing
        PlaybackGlyph.Paused -> R.drawable.ic_pause to R.string.designsystem_state_paused
    }

private fun NetworkIndicator.iconAndLabel(): Pair<Int, Int> =
    when (this) {
        NetworkIndicator.Wifi -> R.drawable.ic_wifi to R.string.designsystem_network_wifi
        NetworkIndicator.Cellular -> R.drawable.ic_signal_cellular_alt to R.string.designsystem_network_cellular
        NetworkIndicator.Offline -> R.drawable.ic_cloud_off to R.string.designsystem_network_offline
    }

@Preview(widthDp = 360)
@Composable
private fun StatusHeaderPreview(
    @PreviewParameter(RotorPaletteProvider::class) palette: RotorPalette,
) {
    RotorPreviewSurface(palette) {
        StatusHeader(title = "Music", playback = PlaybackGlyph.Playing, network = NetworkIndicator.Offline)
    }
}
