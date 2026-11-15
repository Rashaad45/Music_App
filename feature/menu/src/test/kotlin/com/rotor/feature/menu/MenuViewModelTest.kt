package com.rotor.feature.menu

import app.cash.turbine.test
import com.rotor.feature.menu.data.StaticMenuTreeProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MenuViewModelTest {
    private fun viewModel() = MenuViewModel(StaticMenuTreeProvider())

    @Test
    fun `starts on the root menu with the first row focused`() {
        val state = viewModel().uiState.value
        assertEquals(R.string.menu_root_title, state.titleRes)
        assertEquals(listOf("music", "search", "shuffle", "nowplaying", "settings"), state.rows.map { it.id })
        assertEquals(0, state.focusedIndex)
    }

    @Test
    fun `submenus are marked with children`() {
        val rows =
            viewModel()
                .uiState.value.rows
                .associateBy { it.id }
        assertTrue(rows.getValue("music").hasChildren)
        assertTrue(rows.getValue("settings").hasChildren)
        assertEquals(false, rows.getValue("search").hasChildren)
    }

    @Test
    fun `tapping a row focuses it`() =
        runTest {
            val vm = viewModel()
            vm.uiState.test {
                assertEquals(0, awaitItem().focusedIndex)
                vm.onRowClicked(3)
                assertEquals(3, awaitItem().focusedIndex)
            }
        }

    @Test
    fun `out of range taps are ignored`() {
        val vm = viewModel()
        vm.onRowClicked(99)
        vm.onRowClicked(-1)
        assertEquals(0, vm.uiState.value.focusedIndex)
    }
}
