import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.library")
        extensions.configure<LibraryExtension> {
            compileSdk = RotorSdk.COMPILE
            defaultConfig {
                minSdk = RotorSdk.MIN
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            lint {
                targetSdk = RotorSdk.TARGET
                abortOnError = true
                disable += RotorLint.DISABLED
            }
            testOptions {
                targetSdk = RotorSdk.TARGET
                unitTests.isReturnDefaultValues = true
                // Needed by AGP's Compose Preview tasks.
                unitTests.isIncludeAndroidResources = true
            }
        }
        dependencies {
            add("testImplementation", libs.lib("junit"))
        }
        configureQuality()
    }
}
