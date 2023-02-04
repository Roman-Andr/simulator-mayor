group = "me.slavita.construction.service"
version = "1.0"

subprojects {
    dependencies {
        implementation(project(":common"))
        implementation(project(":protocol"))

        compileOnly("ru.cristalix.core:microservice:1.0.12-3")
        compileOnly("me.func:service-api:2.0.0-SNAPSHOT")

        implementation("io.netty:netty-all:4.1.86.Final")
        implementation("com.google.code.gson:gson:2.10.1")
        implementation("it.unimi.dsi:fastutil:8.5.11")
        implementation("com.google.guava:guava:23.0")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")

        implementation("com.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.7")
    }
}
