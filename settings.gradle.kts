pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://repo.repsy.io/mvn/chrynan/public") }
    }
}

rootProject.name = "Noctal"
include(":androidApp")
include(":shared")
