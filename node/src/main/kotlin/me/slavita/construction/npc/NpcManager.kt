package me.slavita.construction.npc

import me.func.mod.world.Npc
import me.func.mod.world.Npc.location
import me.func.mod.world.Npc.onClick
import me.func.mod.world.Npc.skin
import me.func.protocol.world.npc.NpcBehaviour
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.inventory.EquipmentSlot
import java.util.*

object NpcManager {
    private val labels = app.mainWorld.getNpcLabels()

    init {
        NpcType.values().forEach { type ->
            Npc.npc {
                labels.find { it.tag.equals(type.labelTag)}?.let { location(it) }
                name = type.title
                skin(UUID.fromString(type.skin))
                behaviour = NpcBehaviour.STARE_AND_LOOK_AROUND
                onClick {
                    it.player.performCommand(type.command)
                }
            }.slot(EquipmentSlot.HAND, CraftItemStack.asNMSCopy(ItemIcons.get(type.itemKey, type.itemValue)))
        }
    }
}