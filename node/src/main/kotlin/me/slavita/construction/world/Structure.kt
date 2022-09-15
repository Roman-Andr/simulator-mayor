package me.slavita.construction.world

import dev.implario.bukkit.world.V3
import me.func.mod.conversation.ModTransfer
import me.func.mod.util.listener
import me.func.unit.Building
import me.slavita.construction.app
import me.slavita.construction.multichat.MultiChatUtil
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import java.util.UUID

class Structure(val world: GameWorld, val owner : UUID, structureType: Structures, val buildingLocation: Location) {
    private val building = Building(owner, "structure", structureType.id, app.structureMap)
    private lateinit var nextBlock: Block
    private lateinit var nextBlockLocation: V3
    var buildingCompleted = false
    var blocksPlaced = 0
    var blocksSkipped = 0
    var blocksLeft = 0

    init {
        building.box!!.forEachBukkit {
            if (it.block.type != Material.AIR) blocksLeft++
        }
    }

    fun show(player: Player) {
        if (building.allocation == null) building.allocate(buildingLocation)
        building.show(player)
    }

    fun startBuilding() {
        updateNextBlock()
        sendNextBlock()
    }

    fun tryPlaceBlock(location: Location) {
        updateNextBlock()
        if (buildingCompleted) return

        if (location.blockX == nextBlockLocation.x.toInt() ||
            location.blockY == nextBlockLocation.y.toInt() ||
            location.blockZ == nextBlockLocation.z.toInt()) return

        placeNextBlock()
    }

    fun placeNextBlock() {
        updateNextBlock()
        if (buildingCompleted) return

        world.placeBlock(Bukkit.getPlayer(owner), nextBlock, nextBlockLocation)
        blocksPlaced++
        blocksLeft--

        updateNextBlock()
        sendNextBlock()
    }

    private fun sendNextBlock() {
        ModTransfer()
            .integer(nextBlockLocation.x.toInt())
            .integer(nextBlockLocation.y.toInt())
            .integer(nextBlockLocation.z.toInt())
            .integer(nextBlock.type.id)
            .integer(blocksLeft)
            .byte(nextBlock.data)
            .send("structure:next", Bukkit.getPlayer(owner))
    }

    private fun updateNextBlock() {
        if (buildingCompleted) return

        while (true) {
            val dimensions = building.box!!.dimensions
            val dimensionsXZ = (dimensions.x * dimensions.z).toInt()

            val blocksCount = (blocksPlaced + blocksSkipped + 1)
            val blocksLeft = blocksCount % dimensionsXZ
            val y = blocksCount / dimensionsXZ

            if (y > dimensions.y) {
                buildingCompleted = true
                ModTransfer().send("structure:completed", Bukkit.getPlayer(owner))
                break
            }
            val x = blocksLeft / dimensions.z.toInt()
            val z = blocksLeft % dimensions.z.toInt()

            nextBlock = app.structureMap.world.getBlockAt(toLocalLocation(x, y, z))
            nextBlockLocation = V3.of(buildingLocation.x + x, buildingLocation.y + y, buildingLocation.z + z)

            if (nextBlock.type != Material.AIR) break

            blocksSkipped++
        }
    }

    private fun toLocalLocation(x: Int, y: Int, z: Int): Location {
        val blockX = building.box!!.min.blockX + x
        val blockY = building.box!!.min.blockY + y
        val blockZ = building.box!!.min.blockZ + z
        return Location(world.map.world, blockX.toDouble(), blockY.toDouble(), blockZ.toDouble())
    }
}