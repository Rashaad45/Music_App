package com.rotor.feature.menu

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.rotor.core.designsystem.component.NetworkIndicator
import com.rotor.core.designsystem.component.PlaybackGlyph
import com.rotor.feature.menu.data.MenuTreeProvider
import com.rotor.feature.menu.model.MenuNode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class MenuUiState(
    @param:StringRes val titleRes: Int,
    val rows: List<MenuNode>,
    val focusedIndex: Int = 0,
    val playback: PlaybackGlyph = PlaybackGlyph.None,
    val network: NetworkIndicator = NetworkIndicator.Wifi,
)

/**
 * Milestone 1: shows the root menu and moves focus on tap. The menu stack (open/back), keyboard
 * focus and transitions arrive with the menu engine in milestone 2.
 */
@HiltViewModel
class MenuViewModel
    @Inject
    constructor(
        treeProvider: MenuTreeProvider,
    ) : ViewModel() {
        private val root = treeProvider.root()

        private val _uiState = MutableStateFlow(MenuUiState(titleRes = root.titleRes, rows = root.children))
        val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

        fun onRowClicked(index: Int) {
            _uiState.update { state ->
                if (index in state.rows.indices) state.copy(focusedIndex = index) else state
            }
        }
    }
