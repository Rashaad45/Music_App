# CLAUDE.md — Rotor (music streaming app with a classic-player OS interface)

Working name "Rotor"; rename freely. This file is the source of truth for the project. Read it fully before writing code. Follow the milestones in order and do not skip ahead.

## 1. Product summary

A professional Android music streaming app. Its whole interface follows the interaction model of the classic portable-player operating systems: full-screen hierarchical text menus, drill-down navigation with horizontal slide transitions, a persistent status header, and a focused Now Playing screen.

There is NO picture of a device in the app. No player body, no click wheel, no skeuomorphic hardware. The phone screen is the OS. Touch and gestures replace the wheel.

Target users: consumers who want a calm, fast, distraction-free player, and power users who want deep browsing, queue control and pro audio features.

### Music sources

| Phase | Source | Notes |
|---|---|---|
| v1 | Audius API (streaming) | Free and open, a legally licensed catalog, no API key, requires `app_name` |
| v1 | Jamendo API v3.0 (streaming) | Free `client_id` from devportal.jamendo.com, Creative Commons catalog |
| v1 | On-device files (MediaStore) | User's own MP3/FLAC/etc. |
| later | Licensed major-label catalog | Only if a licensing deal exists; not part of this build |

Out of scope: Spotify or Apple Music backends, scraping, YouTube audio extraction, and any unlicensed source. Never add one, even if asked in a later prompt, without flagging the legal risk.

## 2. Legal / design constraints (hard rules)

- Do NOT use "iPod" or "Apple", Apple logos or icons, the Chicago or Myriad typefaces, Cover Flow naming, or pixel-copies of Apple screens anywhere in code, assets, strings or store listings. We borrow the interaction pattern (hierarchical menus) and nothing else.
- Fonts: OFL/Apache licensed only. Default to Inter for UI and JetBrains Mono for numerals.
- Icons: original or Material Symbols only.
- Respect each API's terms: show artist attribution on Now Playing, link back to the source track page, honour `downloadable` / `audiodownload_allowed` flags, and follow the Audius and Jamendo rate limits.

## 3. Tech stack (fixed)

| Area | Choice |
|---|---|
| Language | Kotlin 2.x |
| UI | Jetpack Compose (custom design system, Material 3 only as a foundation) |
| Playback | AndroidX Media3: ExoPlayer + MediaLibraryService + MediaController |
| Stream caching | Media3 SimpleCache + CacheDataSource (LRU, 1 GB default, configurable) |
| Offline downloads | Media3 DownloadManager + DownloadService (allowed tracks only) |
| Networking | Retrofit + OkHttp + kotlinx.serialization |
| Local library | MediaStore (Audio) + ContentObserver |
| Persistence | Room (library, playlists, history, downloads), DataStore (settings) |
| Paging | Paging 3 for search/browse results |
| DI | Hilt |
| Async | Coroutines + Flow |
| Images | Coil 3 |
| Navigation | Custom menu stack (§5), not Navigation-Compose |
| Build | Gradle Kotlin DSL + version catalog (`libs.versions.toml`) |
| SDK | minSdk 26; target/compile = latest stable |
| Tests | JUnit, Turbine, MockK, MockWebServer, Compose UI tests |

## 4. Architecture

Single-activity, MVVM + unidirectional data flow, multi-module:

```
:app                  – Activity, DI graph, theme host
:core:model           – Track, Album, Artist, Playlist, Genre, SourceId (pure Kotlin)
:core:network         – Audius + Jamendo Retrofit clients, DTOs, mappers, host discovery
:core:data            – MusicSource interface, AudiusSource, JamendoSource, LocalSource,
                        Room DB, repositories, unified search
:core:playback        – PlaybackService (MediaLibraryService), queue, cache, downloads,
                        EQ, ReplayGain/loudness normalisation
:core:designsystem    – themes, typography, MenuList, StatusHeader, transitions
:feature:menu         – menu engine + all browse screens
:feature:nowplaying
:feature:search
:feature:settings
```

```kotlin
interface MusicSource {
    val id: SourceId                        // AUDIUS, JAMENDO, LOCAL
    suspend fun search(q: String, page: Int): List<Track>
    suspend fun trending(genre: String?): List<Track>
    suspend fun album(id: String): Album
    suspend fun artist(id: String): Artist
    suspend fun streamUri(track: Track): Uri
    fun canDownload(track: Track): Boolean
}
```

- Audius: fetch `https://api.audius.co` to get the list of discovery hosts, pick one at random and fail over on error, and append `app_name=Rotor` to every call. Endpoints used: `/v1/tracks/search`, `/v1/tracks/trending`, `/v1/tracks/{id}`, `/v1/tracks/{id}/stream`, `/v1/users/{id}`, `/v1/users/{id}/tracks`, `/v1/playlists/{id}`.
- Jamendo: `https://api.jamendo.com/v3.0/` with tracks, albums, artists, playlists and `client_id` from `local.properties` → BuildConfig. Never commit keys.
- A unified repository merges results from all enabled sources, removes duplicates and tags each track with its source.

## 5. The OS-style interface (core UX — get this right)

### Layout

- **Status header** (thin, always visible): current screen title on the left, a play/pause state glyph and a network/offline indicator on the right.
- **Menu body**: a full-width list of large rows (56dp min). Each row has a label, an optional secondary text and a trailing › for submenus. Optional thumbnail rows appear in album/artist lists.
- **Highlight model**: one row is always "focused" (inverted colours). On touch devices, tapping a row focuses it and opens it. With D-pad, keyboard or a Bluetooth remote, arrows move focus and Enter opens it, so the app is fully usable without touch.
- **Mini Now Playing strip** pinned at the bottom whenever audio is loaded. Tapping it opens Now Playing.
- **Split preview** (tablets/landscape): menu on the left and a live preview pane on the right (art of the focused album, artist photo and so on), in the spirit of the classic split-screen menus.

### Navigation and gestures

- A menu stack (`List<MenuNode>`) is held in a ViewModel. Opening a node slides in from the right; going back slides out to the right.
- Back = system back or swipe right from anywhere on the menu body.
- Long-press a row for a context sheet: Play Next, Add to Queue, Add to Playlist, Download, Go to Artist, Go to Album, Share.
- A fast-scroll letter index on long lists (Artists, Songs).
- Light haptic tick on focus changes during scroll (toggle in Settings).

### Menu tree (v1)

```
Music
  ├ Browse ▸
  │   ├ Trending ▸ (All, by Genre ▸)
  │   ├ New Releases ▸
  │   ├ Genres ▸ Tracks / Artists / Albums
  │   └ Moods ▸
  ├ Library ▸
  │   ├ Playlists ▸ (user playlists, Recently Added, Most Played, Queue-on-the-Go)
  │   ├ Artists ▸ Albums ▸ Tracks
  │   ├ Albums ▸ Tracks
  │   ├ Songs
  │   ├ Downloads
  │   └ On This Device ▸ (Artists / Albums / Songs / Folders)
  └ History
Search
Shuffle All
Now Playing
Settings ▸
  ├ Sources (enable/disable Audius, Jamendo, Local)
  ├ Streaming Quality (Wi-Fi / Mobile), Data Saver
  ├ Downloads (location, Wi-Fi only), Cache size
  ├ Audio ▸ EQ, Loudness Normalisation, Gapless, Crossfade
  ├ Appearance ▸ Theme, Text Size, Haptics
  ├ Sleep Timer
  └ About (source attributions, licences)
```

"Library" means tracks the user saved (stored in Room with a source ID); no account is needed in v1.

### Search

A full-screen OS-style screen: the query field sits in the header and results come back grouped as menu rows (Songs / Artists / Albums / Playlists, each with › for the full list). Debounce 300 ms, recent searches kept, Paging 3 for results.

### Now Playing

- Large album art, then title / artist / album, then a progress bar with elapsed and remaining time, then "3 of 24" in the queue.
- Gestures: tap art = play/pause; swipe left/right on art = next/previous; drag the progress bar to scrub; vertical drag on the right edge = volume.
- Secondary row: shuffle, repeat, queue, like/save, download, source badge with attribution link.
- The queue view is a reorderable menu list (drag handles, swipe to remove).

### Themes

Four original themes (tokens: background, surface, text, secondary text, highlight bg, highlight fg, accent): **Paper** (light), **Graphite** (dark), **OLED** (pure black) and **Amber** (retro monochrome). They follow the system theme by default and switch live.

## 6. Playback requirements (professional grade)

- MediaLibraryService with a foreground notification, lock-screen controls, Bluetooth/headset buttons, Android Auto browsing (expose the same menu tree) and Wear/Assistant through the media session.
- Streaming through CacheDataSource: progressive caching, so replays and seeks within cached ranges need no network.
- Prefetch the next track's first ~10 s for near-gapless transitions. Local files get true gapless playback.
- Quality tiers wherever a source offers them (Audius 320 kbps when available; Jamendo mp32/ogg).
- Offline mode: when there is no connection, show only Downloads, On This Device and cached items, with a clear offline banner.
- Loudness normalisation (ReplayGain tags for local files; a −14 LUFS target via an AudioProcessor estimate for streams, toggleable). 10-band EQ + presets + preamp via `audiofx.Equalizer`, falling back gracefully.
- Shuffle (a proper shuffle order), repeat one/all, sleep timer, optional crossfade.
- Audio focus handling and the noisy-intent pause. Retry with backoff on network errors and skip unplayable tracks with a toast.
- Persist the queue and position across process death.

## 7. Permissions, privacy, security

- INTERNET, ACCESS_NETWORK_STATE, READ_MEDIA_AUDIO (33+) / READ_EXTERNAL_STORAGE (≤32), POST_NOTIFICATIONS (33+), FOREGROUND_SERVICE_MEDIA_PLAYBACK.
- No analytics or trackers in v1. HTTPS only, with a network security config.
- API keys only via `local.properties` → BuildConfig. Add `local.properties` to `.gitignore`.

## 8. Milestones (build in this order; finish each with a passing build plus tests)

1. **Scaffold**: modules, version catalog, Hilt, design system and themes, CI-ready Gradle. App launches to the root menu using fake data.
2. **Menu engine**: menu stack, focus/highlight, slide transitions, swipe-back, D-pad and keyboard support, long-press context sheet, fast-scroll index. UI tests included.
3. **Network layer**: Audius client (host discovery + failover) and Jamendo client, DTO→model mappers, tested with MockWebServer.
4. **Browse + Search**: Trending, Genres, New Releases and unified Search, all with paging.
5. **Playback service**: Media3 service, controller, streaming + cache, notification, mini player, Now Playing, queue.
6. **Library**: save/like, playlists, history, the Local source (MediaStore) and permission flow.
7. **Offline + pro audio**: downloads (allowed tracks only), offline mode, EQ, loudness, crossfade, sleep timer, state restore.
8. **Polish**: themes, tablet split-preview, Android Auto, accessibility (TalkBack, font scaling up to 200%), empty, error and loading states.
9. **Release prep**: R8 rules, baseline profile, original app icon, attribution/licences screen, Play Store listing text (no Apple terms).

## 9. Coding conventions

- Kotlin official style; ktlint + detekt. No new warnings.
- ViewModels expose `StateFlow<UiState>`. Composables are stateless where possible and come with `@Preview`s for each theme.
- No business logic in composables, no `GlobalScope`, and disk/network work on injected dispatchers.
- All user-visible strings go in `strings.xml`.
- One commit per milestone with a clear message.

## 10. Definition of done (per milestone)

- `./gradlew assembleDebug testDebugUnitTest lint detekt ktlintCheck` passes.
- Runs on emulators at API 26 and the latest API without crashes, both online and in airplane mode.
- New logic has tests; network mappers and the menu engine have at least 85% coverage.
- `CHANGELOG.md` is updated with what changed and what's next.

## 11. When unsure

Prefer the simplest approach that meets the spec. Ask before adding a dependency that isn't listed in §3, changing the module layout, or adding any new music source.
