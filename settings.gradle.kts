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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        maven {
            url = uri("https://maven.pkg.github.com/yuheegyu/bluetooth-android-maven-repo")

            credentials {
                username = "yuheegyu"
                password = "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"
            }
        }
    }
}

rootProject.name = "polihealth-sdk-android-app"
include(":app")
include(":polihealth-sdk-android")
