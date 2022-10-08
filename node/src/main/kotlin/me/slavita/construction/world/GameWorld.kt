package me.slavita.construction.world

import me.func.world.WorldMeta
import net.minecraft.server.v1_12_R1.CreativeModeTab
import net.minecraft.server.v1_12_R1.Material
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Listener

class GameWorld(val map: WorldMeta) : Listener {

    fun placeFakeBlock(player: Player, block: StructureBlock) {
        player.sendBlockChange(
            Location(map.world, block.position.x.toDouble(), block.position.y.toDouble(), block.position.z.toDouble()),
            block.type,
            block.sourceData
        )
    }

    fun getSpawn() = map.label("spawn")

    fun getNpcLabels() = map.labels("npc")
}
