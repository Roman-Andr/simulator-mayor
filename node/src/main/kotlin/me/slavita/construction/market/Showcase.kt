package me.slavita.construction.market

import me.func.mod.util.listener
import me.slavita.construction.action.command.menu.ShowcaseMenu
import me.slavita.construction.app
import me.slavita.construction.utils.extensions.BlocksExtensions.toV3
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

class Showcase(val properties: ShowcaseProperties) : Listener {
    private val box = app.mainWorld.map.getBox("showcase", properties.boxName)

    init {
        listener(this)
    }

    @EventHandler
    fun PlayerInteractEvent.handle() {
        if (action != Action.RIGHT_CLICK_BLOCK || !box.contains(blockClicked.location)) return
        ShowcaseMenu(player, properties.name, app.allBlocks.filter { properties.materials.contains(it.type) }).tryExecute()
    }

    fun getData(): ShowcaseDataForClient {
        return ShowcaseDataForClient(properties.name, box.min.toV3(), box.max.toV3(), UUID.randomUUID())
    }
}