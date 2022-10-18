package me.slavita.construction.utils

import me.func.atlas.Atlas
import me.func.mod.util.log

object Config {
    init {
        Atlas.config(
            listOf(
                "https://storage.c7x.dev/romanandr/construction/config/worker.yaml",
            )
        ).forEach { file ->
            file.thenAccept {
                log("Loaded config ${it.fileName}")
            }
        }
    }
}