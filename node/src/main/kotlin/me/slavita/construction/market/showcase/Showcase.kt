package me.slavita.construction.market.showcase

import me.func.mod.Anime
import me.func.mod.util.listener
import me.slavita.construction.action.command.menu.ShowcaseMenu
import me.slavita.construction.app
import me.slavita.construction.utils.extensions.BlocksExtensions.toV3
import org.bukkit.event.Listener

class Showcase(val properties: ShowcaseProperties) : Listener {
    private val box = app.mainWorld.map.getBox("showcase", properties.id.toString())

    init {
        listener(this)

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