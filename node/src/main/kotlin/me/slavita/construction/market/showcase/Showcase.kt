package me.slavita.construction.market.showcase

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.ShowcaseMenu
import me.slavita.construction.app
import me.slavita.construction.utils.extensions.BlocksExtensions.toV3

class Showcase(val properties: ShowcaseProperties) {
    private val box = app.mainWorld.map.getBox("showcase", properties.id.toString())

    init {
        Anime.createReader("showcase:open") { player, buff ->
            if (buff.readInt() != properties.id) return@createReader

            ShowcaseMenu(
                player,
                properties.name,
                properties.elements
            ).tryExecute()
        }
    }

    fun getData(): ShowcaseClientData {
        return ShowcaseClientData(properties.id, properties.name, box.min.toV3(), box.max.toV3())
    }
}