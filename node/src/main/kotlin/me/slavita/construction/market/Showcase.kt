package me.slavita.construction.market

import me.func.mod.util.listener
import me.func.world.Box
import me.slavita.construction.action.command.menu.ShowcaseMenu
import me.slavita.construction.app
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

class Showcase(val properties: ShowcaseProperties) : Listener {
    val box = app.mainWorld.map.getBox("showcase", properties.boxName)

    init {
        listener(this)
    }

    @EventHandler
    fun onInteractEvent(event: PlayerInteractEvent) {
        if (event.action != Action.RIGHT_CLICK_BLOCK || !box.contains(event.blockClicked.location)) return
        ShowcaseMenu(event.player, properties.name, app.allBlocks.filter { properties.materials.contains(it.type) }).tryExecute()
    }
}