package me.slavita.construction.world

import me.func.mod.util.command
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.world.WorldMeta
import me.slavita.construction.utils.banner.BannerInfo
import me.slavita.construction.utils.banner.BannerUtil
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.Listener

class GameWorld(val map: WorldMeta) : Listener {

    init {
        command("show") { player, _ ->
            Banners.show(player, *BannerUtil.createDual(BannerInfo(
                player.location.toCenterLocation(),
                BlockFace.WEST,
                listOf(Pair("Ага привет как дела", 0.5)),
                64,
                16,
                GlowColor.GREEN
            )).toList().toTypedArray())
        }
    }

    fun placeFakeBlock(player: Player, block: BlockProperties) {
        player.sendBlockChange(
            Location(map.world, block.position.x.toDouble(), block.position.y.toDouble(), block.position.z.toDouble()),
            block.type,
            block.data
        )
    }

    fun getSpawn() = map.label("spawn")

    fun getNpcLabels() = map.labels("npc")
}
