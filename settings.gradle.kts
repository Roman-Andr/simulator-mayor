@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://repo.c7x.dev/repository/maven-public/")
            credentials {
                username = System.getenv("CRI_REPO_LOGIN") ?: System.getenv("CRI_ARC_REPO_LOGIN")
                    ?: System.getenv("CRISTALIX_REPO_USERNAME")
                password = System.getenv("CRI_REPO_PASSWORD") ?: System.getenv("CRI_ARC_REPO_PASSWORD")
                    ?: System.getenv("CRISTALIX_REPO_PASSWORD")
            }
        }
        maven {
            url = uri("https://repo.c7x.dev/repository/arcades/")
            credentials {
                username = System.getenv("CRI_REPO_LOGIN") ?: System.getenv("CRI_ARC_REPO_LOGIN")
                    ?: System.getenv("CRISTALIX_REPO_USERNAME")
                password = System.getenv("CRI_REPO_PASSWORD") ?: System.getenv("CRI_ARC_REPO_PASSWORD")
                    ?: System.getenv("CRISTALIX_REPO_PASSWORD")
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
        id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
        id("org.hidetake.ssh") version "2.10.1"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.c7x.dev/repository/maven-public/")
            credentials {
                username = System.getenv("CRI_REPO_LOGIN") ?: System.getenv("CRI_ARC_REPO_LOGIN")
                    ?: System.getenv("CRISTALIX_REPO_USERNAME")
                password = System.getenv("CRI_REPO_PASSWORD") ?: System.getenv("CRI_ARC_REPO_PASSWORD")
                    ?: System.getenv("CRISTALIX_REPO_PASSWORD")
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
    ":service",
    ":mod",
    ":node",
)
