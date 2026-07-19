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

rootProject.name = "SteamTraderKeys"
include(":app")
include(":modules")
include(":modules:arch")
include(":modules:arch-android")
include(":modules:data")
include(":modules:features")
include(":modules:features:settings")
include(":modules:common-ui")
include(":modules:features:trader")
include(":modules:features:trader-data")
include(":modules:common-data")
