plugins {
    id("anime.mod-bundler")
}

group = "me.slavita.construction.mod.uimod"
version = "1.0"

dependencies {
    implementation("ru.cristalix:ui-engine-v2:1.3.11")
}

mod {
    name = "visualmod"
    main = "me.slavita.construction.mod.uimod.App"
    jarFileName = "construction-uimod-bundle.jar"
    author = "Slavita, RomanAndr"
    setVersion("1.0.0")
}
