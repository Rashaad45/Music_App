import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        // AGP 9 compiles Kotlin itself ("built-in Kotlin"); no kotlin-android plugin needed.
        pluginManager.apply("com.android.application")
        extensions.configure<ApplicationExtension> {
            compileSdk = RotorSdk.COMPILE
            defaultConfig {
                minSdk = RotorSdk.MIN
                targetSdk = RotorSdk.TARGET
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            lint {
                abortOnError = true
                disable += RotorLint.DISABLED
            }
            testOptions {
                unitTests.isReturnDefaultValues = true
                // Needed by AGP's Compose Preview tasks.
                unitTests.isIncludeAndroidResources = true
            }
            packaging {
                resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        configureQuality()
    }
}
