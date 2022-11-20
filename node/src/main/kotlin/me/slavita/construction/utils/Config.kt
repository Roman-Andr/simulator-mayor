package me.slavita.construction.utils

import me.func.atlas.Atlas
import me.slavita.construction.utils.extensions.LoggerUtils.log

object Config {
    fun load(after: () -> Unit) {
        var loaded = 0
        val configs = listOf(
            "worker",
            "npc",
            "bank",
            "boards",
            "dialogs",
        ).map {
            "https://storage.c7x.dev/romanandr/construction/config/$it.yml"
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