package com.rotor.feature.menu.data

import com.rotor.feature.menu.R
import com.rotor.feature.menu.model.MenuNode
import javax.inject.Inject

/** Supplies the root of the menu tree. */
fun interface MenuTreeProvider {
    fun root(): MenuNode
}

/**
 * The static v1 menu tree from the spec (§5). Dynamic content (tracks, artists, playlists) is
 * filled in from sources in later milestones; for now every node is fixed.
 */
class StaticMenuTreeProvider
    @Inject
    constructor() : MenuTreeProvider {
        override fun root(): MenuNode = ROOT

        private companion object {
            val BROWSE =
                node(
                    "browse",
                    R.string.menu_browse,
                    node("browse/trending", R.string.menu_trending, leaf("trending/all", R.string.menu_all)),
                    leaf("browse/new", R.string.menu_new_releases),
                    leaf("browse/genres", R.string.menu_genres),
                    leaf("browse/moods", R.string.menu_moods),
                )

            val LIBRARY =
                node(
                    "library",
                    R.string.menu_library,
                    node(
                        "library/playlists",
                        R.string.menu_playlists,
                        leaf("playlists/recent", R.string.menu_recently_added),
                        leaf("playlists/most", R.string.menu_most_played),
                        leaf("playlists/otg", R.string.menu_queue_on_the_go),
                    ),
                    leaf("library/artists", R.string.menu_artists),
                    leaf("library/albums", R.string.menu_albums),
                    leaf("library/songs", R.string.menu_songs),
                    leaf("library/downloads", R.string.menu_downloads),
                    node(
                        "library/device",
                        R.string.menu_on_this_device,
                        leaf("device/artists", R.string.menu_artists),
                        leaf("device/albums", R.string.menu_albums),
                        leaf("device/songs", R.string.menu_songs),
                        leaf("device/folders", R.string.menu_folders),
                    ),
                )

            val SETTINGS =
                node(
                    "settings",
                    R.string.menu_settings,
                    leaf("settings/sources", R.string.menu_sources),
                    leaf("settings/quality", R.string.menu_streaming_quality),
                    leaf("settings/downloads", R.string.menu_downloads),
                    leaf("settings/audio", R.string.menu_audio),
                    leaf("settings/appearance", R.string.menu_appearance),
                    leaf("settings/sleep", R.string.menu_sleep_timer),
                    leaf("settings/about", R.string.menu_about),
                )

            val ROOT =
                node(
                    "root",
                    R.string.menu_root_title,
                    node("music", R.string.menu_music, BROWSE, LIBRARY, leaf("music/history", R.string.menu_history)),
                    leaf("search", R.string.menu_search),
                    leaf("shuffle", R.string.menu_shuffle_all),
                    leaf("nowplaying", R.string.menu_now_playing),
                    SETTINGS,
                )

            fun node(
                id: String,
                titleRes: Int,
                vararg children: MenuNode,
            ) = MenuNode(id, titleRes, children.toList())

            fun leaf(
                id: String,
                titleRes: Int,
            ) = MenuNode(id, titleRes)
        }
    }
