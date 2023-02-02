plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.hidetake.ssh")
    id("org.jlleitschuh.gradle.ktlint")
}

allprojects {
    group = "me.slavita.construction"
    version = "1.0"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "org.jetbrains.kotlin.jvm")
//    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
