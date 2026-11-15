package com.rotor.feature.menu.data

import com.rotor.feature.menu.model.MenuNode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class StaticMenuTreeProviderTest {
    private val root = StaticMenuTreeProvider().root()

    private fun MenuNode.child(id: String) = children.first { it.id == id }

    private fun MenuNode.allIds(): List<String> = listOf(id) + children.flatMap { it.allIds() }

    @Test
    fun `music contains browse, library and history`() {
        assertEquals(listOf("browse", "library", "music/history"), root.child("music").children.map { it.id })
    }

    @Test
    fun `library matches the v1 tree`() {
        val library = root.child("music").child("library")
        assertEquals(
            listOf(
                "library/playlists",
                "library/artists",
                "library/albums",
                "library/songs",
                "library/downloads",
                "library/device",
            ),
            library.children.map { it.id },
        )
    }

    @Test
    fun `node ids are unique`() {
        val ids = root.allIds()
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `settings has seven sections`() {
        val settings = root.child("settings")
        assertEquals(7, settings.children.size)
        assertTrue(settings.hasChildren)
    }
}
