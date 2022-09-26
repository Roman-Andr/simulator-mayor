plugins {
    id("org.jetbrains.kotlin.jvm")
}

allprojects {
    group = "me.slavita.construction"
    version = "1.0"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
}