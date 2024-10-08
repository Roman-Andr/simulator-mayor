plugins {
    id("com.github.johnrengelman.shadow")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":protocol"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.0")

    compileOnly("ru.cristalix.core:bukkit-api:1.0.18.15-TEST")
    implementation("ru.cristalix:boards-bukkit-api:3.0.16")

    compileOnly("cristalix:dark-paper:21.02.03")

    implementation("dev.implario.bukkit:dark-paper:1.0.0")
    implementation("dev.implario.bukkit:kotlin-api:1.1.1")
    implementation("dev.implario.bukkit:bukkit-tools:4.4.12")

    implementation("implario:humanize:1.1.3")
    implementation("implario:bukkit-worker-core:2.1.20")

    implementation("me.func:visual-driver:3.3.9.TEST")
    implementation("me.func:world-api:1.0.15")
    implementation("me.func:metaworld-api:1.0.7")
    implementation("me.func:sound-api:1.0.5")
    implementation("me.func:stronghold:1.1.1.RELEASE")
    implementation("me.func:stronghold-protocol:1.1.1.RELEASE")
    implementation("me.func:lock-service:1.1.0.RELEASE")
    implementation("me.func:lock-service-protocol:1.1.0.RELEASE")

    implementation("com.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.7")
}

afterEvaluate {
    tasks {
        jar {
            dependsOn(":common:jar")
            dependsOn(":protocol:jar")
            archiveBaseName.set("construction")
            from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}
