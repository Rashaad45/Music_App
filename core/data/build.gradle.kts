plugins {
    alias(libs.plugins.rotor.android.library)
}

android {
    namespace = "com.rotor.core.data"
}

dependencies {
    api(projects.core.model)
}
