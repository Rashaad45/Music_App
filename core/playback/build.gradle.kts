// Media3 PlaybackService arrives in milestone 5.
plugins {
    alias(libs.plugins.rotor.android.library)
}

android {
    namespace = "com.rotor.core.playback"
}

dependencies {
    implementation(projects.core.model)
}
