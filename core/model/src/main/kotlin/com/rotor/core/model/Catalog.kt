package com.rotor.core.model

/**
 * Identifies an item across sources. Ids are only unique within their source, so the pair is the
 * real key (used for de-duplication, Room rows and media ids).
 */
data class MediaKey(
    val source: SourceId,
    val id: String,
) {
    override fun toString(): String = "${source.name}:$id"
}

data class Track(
    val key: MediaKey,
    val title: String,
    val artistName: String,
    val durationMs: Long,
    val artistId: String? = null,
    val albumId: String? = null,
    val albumTitle: String? = null,
    val artworkUrl: String? = null,
    val genre: String? = null,
    /** Public page for the track on its source, linked from Now Playing for attribution. */
    val sourcePageUrl: String? = null,
    /** True only when the source's terms allow offline downloads of this track. */
    val isDownloadable: Boolean = false,
    val trackNumber: Int? = null,
) {
    val source: SourceId get() = key.source
}

data class Album(
    val key: MediaKey,
    val title: String,
    val artistName: String,
    val artistId: String? = null,
    val artworkUrl: String? = null,
    val year: Int? = null,
    val tracks: List<Track> = emptyList(),
)

data class Artist(
    val key: MediaKey,
    val name: String,
    val imageUrl: String? = null,
    val albums: List<Album> = emptyList(),
    val tracks: List<Track> = emptyList(),
)

data class Playlist(
    val key: MediaKey,
    val title: String,
    val ownerName: String? = null,
    val artworkUrl: String? = null,
    val tracks: List<Track> = emptyList(),
)

data class Genre(
    val id: String,
    val name: String,
)
