<<<<<<< HEAD
=======

>>>>>>> e808d67db433f496446bc0314bb2fe92d0ebd014
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
<<<<<<< HEAD
    plugins {
        id("org.jetbrains.kotlin.plugin.compose") version "1.9.22"
    }
=======
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
>>>>>>> e808d67db433f496446bc0314bb2fe92d0ebd014
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

<<<<<<< HEAD
rootProject.name = "PharmaConnect"
include(":app")
=======
rootProject.name = "My Application"
include(":app")
>>>>>>> e808d67db433f496446bc0314bb2fe92d0ebd014
