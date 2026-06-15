pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Task Cluster"
include(":app")
// :baselineprofile (macrobenchmark generator) is kept on disk but not built —
// the androidx.baselineprofile plugin doesn't support AGP 9.2 yet. Re-add this
// include once a compatible plugin version ships, then run :app:generateBaselineProfile.
// include(":baselineprofile")
 