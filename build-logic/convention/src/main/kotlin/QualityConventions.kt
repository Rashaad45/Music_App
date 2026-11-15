import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jlleitschuh.gradle.ktlint.KtlintExtension

/** detekt + ktlint, applied by every convention plugin so all modules are checked identically. */
internal fun Project.configureQuality() {
    pluginManager.apply("io.gitlab.arturbosch.detekt")
    pluginManager.apply("org.jlleitschuh.gradle.ktlint")

    extensions.configure<DetektExtension> {
        buildUponDefaultConfig = true
        parallel = true
        config.setFrom(rootProject.file("config/detekt/detekt.yml"))
    }
    tasks.withType<Detekt>().configureEach {
        jvmTarget = "17"
    }
    // detekt 1.23 embeds the Kotlin compiler it was built with; keep newer Kotlin off its classpath.
    val detektKotlin = libs.version("detektKotlin")
    configurations.matching { it.name == "detekt" }.configureEach {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") useVersion(detektKotlin)
        }
    }

    extensions.configure<KtlintExtension> {
        version.set(libs.version("ktlint"))
        android.set(false)
    }
}
