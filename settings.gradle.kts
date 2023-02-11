@file:Suppress("UnstableApiUsage")

pluginManagement {
    fun env(property: String) = providers.gradleProperty(property).orNull

    repositories {
        gradlePluginPortal()
        google()
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        maven {
            url = uri("https://repo.c7x.dev/repository/maven-public/")
            credentials {
                username = env("CRI_REPO_LOGIN") ?: env("CRI_ARC_REPO_LOGIN")
                password = env("CRI_REPO_PASSWORD") ?: env("CRI_ARC_REPO_PASSWORD")
            }
        }
        maven {
            url = uri("https://repo.c7x.dev/repository/arcades/")
            credentials {
                username = env("CRI_REPO_LOGIN") ?: env("CRI_ARC_REPO_LOGIN")
                password = env("CRI_REPO_PASSWORD") ?: env("CRI_ARC_REPO_PASSWORD")
            }
        }
        maven {
            url = uri("https://jitpack.io")
        }
    }

    includeBuild("bundler")

    plugins {
        kotlin("jvm") version "1.8.0"
        id("com.github.johnrengelman.shadow") version "7.1.2"
//        id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
        id("org.hidetake.ssh") version "2.10.1"
    }
}

dependencyResolutionManagement {
    fun env(property: String) = providers.gradleProperty(property).orNull

    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.c7x.dev/repository/maven-public/")
            credentials {
                username = env("CRI_REPO_LOGIN") ?: env("CRI_ARC_REPO_LOGIN")
                password = env("CRI_REPO_PASSWORD") ?: env("CRI_ARC_REPO_PASSWORD")
            }
        }
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "construction"

include(
    ":common",
    ":protocol",
    ":mod",
    ":node",
    ":service",
    ":service:user",
)

findProject(":service:user")?.name = "user"
