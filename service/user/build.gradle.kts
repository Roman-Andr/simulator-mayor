plugins {
    id("com.github.johnrengelman.shadow")
}

group = "me.slavita.construction.service.user"
version = "1.0"

dependencies {
    implementation("org.mongodb:mongodb-driver-async:3.12.11")
    implementation("org.mongodb:mongodb-driver:3.12.11")
}

tasks {
    jar { enabled = false }
    shadowJar {
        archiveFileName.set("user-service-construction.jar")
        configurations = listOf(project.configurations.compileClasspath.get())
        manifest {
            attributes["Main-Class"] = "me.slavita.construction.service.user.MainKt"
            attributes["Multi-Release"] = true
        }
    }
    build { dependsOn(shadowJar) }
}
