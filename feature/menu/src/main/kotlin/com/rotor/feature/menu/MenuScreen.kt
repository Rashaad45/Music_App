package com.rotor.feature.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rotor.core.designsystem.component.MenuItemUi
import com.rotor.core.designsystem.component.MenuList
import com.rotor.core.designsystem.component.StatusHeader
import com.rotor.core.designsystem.preview.RotorPaletteProvider
import com.rotor.core.designsystem.preview.RotorPreviewSurface
import com.rotor.core.designsystem.theme.RotorPalette
import com.rotor.core.designsystem.theme.RotorTheme
import com.rotor.feature.menu.data.StaticMenuTreeProvider

@Composable
fun MenuRoute(
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    MenuScreen(state = state, onRowClick = viewModel::onRowClicked, modifier = modifier)
}

@Composable
fun MenuScreen(
    state: MenuUiState,
    onRowClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val items =
        state.rows.map { node ->
            MenuItemUi(id = node.id, label = stringResource(node.titleRes), hasChildren = node.hasChildren)
        }
    Column(modifier.fillMaxSize().background(RotorTheme.colors.background)) {
        StatusHeader(
            title = stringResource(state.titleRes),
            playback = state.playback,
            network = state.network,
        )
        MenuList(
            items = items,
            focusedIndex = state.focusedIndex,
            onItemClick = onRowClick,
            modifier = Modifier.fillMaxWidth().weight(1f),
        )
    }
}

@Preview(widthDp = 360, heightDp = 480)
@Composable
private fun MenuScreenPreview(
    @PreviewParameter(RotorPaletteProvider::class) palette: RotorPalette,
) {
    val root = StaticMenuTreeProvider().root()
    RotorPreviewSurface(palette) {
        MenuScreen(state = MenuUiState(titleRes = root.titleRes, rows = root.children), onRowClick = {})
    }
}
