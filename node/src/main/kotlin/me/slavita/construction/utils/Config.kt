package me.slavita.construction.utils

import me.func.atlas.Atlas

object Config {
    fun load(action: () -> Unit) {
        var loaded = 0
        val configs = listOf(
            "worker",
            "npc",
            "dialogs",
            "showcases",
            "locations",
            "city",
        ).map {
            "$STORAGE_URL/config/$it.yml"
        }

        Atlas.config(configs).forEach { file ->
            file.thenAccept {
                log("Loaded config ${it.fileName}")
                loaded++
                if (loaded == configs.size) {
                    log("loaded config")
                    action()
                }
            }
        }
    }
}
