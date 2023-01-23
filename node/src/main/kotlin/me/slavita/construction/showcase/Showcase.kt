package me.slavita.construction.showcase

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.ShowcaseMenu
import me.slavita.construction.app
import me.slavita.construction.common.utils.TimeFormatter
import me.slavita.construction.utils.BlocksExtensions.toV3
import me.slavita.construction.utils.scheduler

class Showcase(val properties: ShowcaseProperties) {
    val box
        get() = app.mainWorld.map.getBox("showcase", properties.id.toString())

    fun init() {
        Anime.createReader("showcase:open") { player, buff ->
            if (buff.readInt() != properties.id) return@createReader

            ShowcaseMenu(
                player,
                this
            ).tryExecute()
        }
    }

    fun getData() = ShowcaseClientData(properties.id, properties.name, box.min.toV3(), box.max.toV3())
}
