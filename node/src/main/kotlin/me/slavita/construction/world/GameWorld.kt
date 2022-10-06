package me.slavita.construction.world

import me.func.world.WorldMeta
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.Listener

class GameWorld(val map: WorldMeta) : Listener {
    fun placeFakeBlock(player: Player, block: BlockProperties) {
        player.sendBlockChange(
            Location(map.world, block.position.x.toDouble(), block.position.y.toDouble(), block.position.z.toDouble()),
            block.type,
            block.sourceData.toByte()
        )
    }

    fun getSpawn() = map.label("spawn")

    fun getNpcLabels() = map.labels("npc")
}
