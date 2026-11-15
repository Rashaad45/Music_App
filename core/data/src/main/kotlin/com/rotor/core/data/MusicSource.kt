package com.rotor.core.data

import android.net.Uri
import com.rotor.core.model.Album
import com.rotor.core.model.Artist
import com.rotor.core.model.SourceId
import com.rotor.core.model.Track

/** One catalog (Audius, Jamendo or on-device files). Implementations arrive in milestones 3–6. */
interface MusicSource {
    val id: SourceId

    suspend fun search(
        query: String,
        page: Int,
    ): List<Track>

    suspend fun trending(genre: String?): List<Track>

    suspend fun album(id: String): Album

    suspend fun artist(id: String): Artist

    suspend fun streamUri(track: Track): Uri

    fun canDownload(track: Track): Boolean
}
