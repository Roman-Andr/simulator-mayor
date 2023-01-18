package me.slavita.construction.utils

import me.func.atlas.Atlas

object Config {
    fun load(after: () -> Unit) {
        var loaded = 0
        val configs = listOf(
            "worker",
            "npc",
            "bank",
            "boards",
            "dialogs",
            "showcases",
            "locations",
            "city",
        ).map {
            "${STORAGE_URL}/config/$it.yml"
        }

        Atlas.config(configs).forEach { file ->
            file.thenAccept {
                log("Loaded config ${it.fileName}")
                loaded++
                if (loaded == configs.size) {
                    after()
                }
            }
        }
    }
}