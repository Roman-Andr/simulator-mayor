plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.c7x.dev/repository/arcades/")
        credentials {
            username = System.getenv("CRI_ARC_REPO_LOGIN")
            password = System.getenv("CRI_ARC_REPO_PASSWORD")
        }
    }
}

dependencies {
    implementation("dev.kamillaova.proguard:proguard-gradle:7.2.2")
}

gradlePlugin {
    plugins {
        create("Anime Mod Bundler") {
            id = "anime.mod-bundler"
            implementationClass = "anime.modbundler.ModBundlerPlugin"
        }
    }
}
