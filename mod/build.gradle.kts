subprojects {
    dependencies {
        implementation(project(":common"))

        compileOnly("dev.xdark:clientapi:2.0.4")
        implementation("ru.cristalix:client-sdk:isClientMod-SNAPSHOT")
        implementation("implario:humanize:1.1.3")

        implementation("me.func.visual-driver:visual-driver-protocol:3.3.9.TEST")
    }
}
