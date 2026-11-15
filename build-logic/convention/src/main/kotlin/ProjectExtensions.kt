import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

internal object RotorSdk {
    const val COMPILE = 37
    const val TARGET = 37
    const val MIN = 26
}

internal object RotorLint {
    val DISABLED =
        setOf(
            // Network-dependent and changes daily; version bumps are a deliberate, separate task.
            "NewerVersionAvailable",
            "GradleDependency",
            // Reports that a third-party library's bundled lint jar can't load; nothing to fix here.
            "ObsoleteLintCustomCheck",
        )
}

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.lib(alias: String): Provider<MinimalExternalModuleDependency> =
    findLibrary(alias).orElseThrow { IllegalArgumentException("Missing library '$alias' in libs.versions.toml") }

internal fun VersionCatalog.version(alias: String): String =
    findVersion(alias).orElseThrow { IllegalArgumentException("Missing version '$alias'") }.requiredVersion
