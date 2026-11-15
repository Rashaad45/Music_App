package com.rotor.core.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class CatalogTest {
    @Test
    fun `media key renders as source and id`() {
        assertEquals("AUDIUS:abc", MediaKey(SourceId.AUDIUS, "abc").toString())
    }

    @Test
    fun `same id from different sources is a different key`() {
        assertNotEquals(MediaKey(SourceId.AUDIUS, "1"), MediaKey(SourceId.JAMENDO, "1"))
    }

    @Test
    fun `track exposes the source of its key`() {
        val track = Track(MediaKey(SourceId.LOCAL, "42"), "Title", "Artist", durationMs = 1_000)
        assertEquals(SourceId.LOCAL, track.source)
    }

    @Test
    fun `formats minutes and seconds`() {
        assertEquals("0:00", formatDuration(0))
        assertEquals("0:59", formatDuration(59_999))
        assertEquals("3:05", formatDuration(185_000))
        assertEquals("59:59", formatDuration(3_599_000))
    }

    @Test
    fun `formats hours with padded minutes`() {
        assertEquals("1:00:00", formatDuration(3_600_000))
        assertEquals("2:03:04", formatDuration(7_384_000))
    }

    @Test
    fun `clamps negative durations`() {
        assertEquals("0:00", formatDuration(-5_000))
    }
}
