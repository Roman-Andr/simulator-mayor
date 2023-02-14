plugins {
    id("anime.mod-bundler")
}

dependencies {
    implementation(project(":common"))

    compileOnly("dev.xdark:clientapi:2.0.4")
    implementation("ru.cristalix:ui-engine-v2:1.3.11")
    implementation("ru.cristalix:client-sdk:isClientMod-SNAPSHOT")
//    implementation("ru.cristalix:enginex:1.0.55")

    implementation("implario:humanize:1.1.3")

    implementation("me.func.visual-driver:visual-driver-protocol:3.3.9.TEST")
}

mod {
    name = "construction"
    jarFileName = "construction-mod.jar"
    main = "me.slavita.construction.mod.App"
    author = "Slavita, RomanAndr"
    setVersion("1.0.0")
}
