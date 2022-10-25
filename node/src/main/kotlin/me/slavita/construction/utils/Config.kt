package me.slavita.construction.utils

import me.func.atlas.Atlas
import me.slavita.construction.utils.extensions.LoggerUtils.log

object Config {
	init {
		Atlas.config(
			listOf(
				"https://storage.c7x.dev/romanandr/construction/config/worker.yml",
				"https://storage.c7x.dev/romanandr/construction/config/npc.yml",
				"https://storage.c7x.dev/romanandr/construction/config/bank.yml",
			)
		).forEach { file ->
			file.thenAccept {
				log("Loaded config ${it.fileName}")
			}
		}
	}
}