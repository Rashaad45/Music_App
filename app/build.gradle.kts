plugins {
    alias(libs.plugins.rotor.android.application)
    alias(libs.plugins.rotor.android.compose)
    alias(libs.plugins.rotor.hilt)
}

android {
    namespace = "com.rotor.app"

    defaultConfig {
        applicationId = "com.rotor.app"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.feature.menu)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
}
