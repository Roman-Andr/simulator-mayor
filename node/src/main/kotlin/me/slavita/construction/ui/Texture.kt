package me.slavita.construction.ui

enum class Texture(val fileName: String) {
    SPEED_BOOST("speed-boost.png"),
    LOCATION("tree.png");

    fun path(): String {
        return "cache/animation:${fileName}"
    }
}