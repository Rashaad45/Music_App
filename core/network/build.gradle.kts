// Audius + Jamendo clients arrive in milestone 3.
plugins {
    alias(libs.plugins.rotor.android.library)
}

android {
    namespace = "com.rotor.core.network"
}

dependencies {
    implementation(projects.core.model)
}
