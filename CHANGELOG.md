# Changelog

## Milestone 1 — Scaffold (2026-11-15)

### Added
- **Multi-module build** per §4: `:app`, `:core:{model,network,data,playback,designsystem}`,
  `:feature:{menu,nowplaying,search,settings}`. `network`, `playback`, `nowplaying`, `search` and
  `settings` are empty shells until their milestones.
- **Gradle**: Kotlin DSL, version catalog (`gradle/libs.versions.toml`), type-safe project accessors,
  and `build-logic` convention plugins (`rotor.android.application|library|compose|feature`,
  `rotor.hilt`, `rotor.jvm.library`). Every module gets detekt + ktlint from the conventions.
  Toolchain: Gradle 9.9.1, AGP 9.5.1 (built-in Kotlin), Kotlin 2.3.21, KSP 2.3.14, compile/target
  SDK 37, min SDK 26, JDK 17.
- **CI**: `.github/workflows/ci.yml` runs the definition-of-done command; the Jamendo id comes from
  the `JAMENDO_CLIENT_ID` repository secret.
- **`:core:model`**: `SourceId`, `MediaKey`, `Track`, `Album`, `Artist`, `Playlist`, `Genre`, and
  `formatDuration`.
- **`:core:data`**: the `MusicSource` interface (no implementations yet).
- **`:core:designsystem`**:
  - Four original themes (Paper, Graphite, OLED, Amber) built on the seven spec tokens, plus
    `ThemePreference` (follow system → Paper/Graphite). A test checks WCAG AA contrast for text and
    highlight in every theme.
  - Typography using bundled Inter and JetBrains Mono (OFL; licences in `licenses/`).
  - `StatusHeader` (title, play/pause glyph, Wi-Fi/mobile/offline indicator), `MenuRow` (56dp min,
    inverted highlight, chevron for submenus), `MenuList`, each with a `@Preview` per theme.
  - Material Symbols (Apache 2.0) for icons.
- **`:feature:menu`**: the full v1 menu tree from §5 as static data, `MenuViewModel` exposing
  `StateFlow<MenuUiState>`, and `MenuScreen`. Tapping a row focuses it.
- **`:app`**: Hilt application, single edge-to-edge activity, theme that follows the system and
  switches live, HTTPS-only network security config, backup disabled, and an original launcher icon.

### Notes
- Lint skips `NewerVersionAvailable`/`GradleDependency` (they depend on the network and change from
  day to day) and `ObsoleteLintCustomCheck` (a third-party lint jar). detekt 1.23.8 runs on its own
  Kotlin 2.0.21 classpath.
- The remaining Gradle deprecation notices come from the detekt/ktlint plugins, not from project
  scripts.
- Not yet checked on a device: this machine has no emulator or system image installed.

### Next — Milestone 2: Menu engine
Menu stack (open/back) with slide transitions, swipe-back and system back, D-pad/keyboard focus,
long-press context sheet, fast-scroll letter index, haptic ticks, and Compose UI tests.
