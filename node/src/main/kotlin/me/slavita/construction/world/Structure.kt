package me.slavita.construction.world

import dev.implario.bukkit.world.V3
import me.func.mod.Anime
import me.func.mod.conversation.ModTransfer
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.func.unit.Building
import me.slavita.construction.app
import me.slavita.construction.util.RegisterConnectionUtil.registerChannelRead
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.EnumHand
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

class Structure(val world: GameWorld, val owner : UUID, structureType: Structures, val buildingLocation: Location) {
    private val building = Building(owner, "structure", structureType.id, app.structureMap)
    lateinit var nextBlock: Block
    lateinit var nextBlockLocation: V3
    var buildingCompleted = false
    var blocksPlaced = 0
    var blocksSkipped = 0
    var blocksLeft = 0

    init {
        building.box!!.forEachBukkit {
            if (it.block.type != Material.AIR) blocksLeft++
        }

        val player = Bukkit.getPlayer(owner)
        registerChannelRead(player, { packet ->
            if (packet !is PacketPlayInUseItem) return@registerChannelRead
            if (packet.c != EnumHand.MAIN_HAND) return@registerChannelRead

            val location = Location(
                world.map.world,
                packet.a.x.toDouble(),
                packet.a.y.toDouble(),
                packet.a.z.toDouble()
            ).block.getRelative(BlockFace.valueOf(packet.b.name)).location
            println(packet.a)
            println(location)
            tryPlaceBlock(player.inventory.itemInMainHand, location)
        }, { packet ->
            if (packet !is PacketPlayOutBlockChange) return@registerChannelRead true
            if (packet.a.x < buildingLocation.x || packet.a.x > buildingLocation.x + building.box!!.dimensions.x) return@registerChannelRead true
            if (packet.a.y < buildingLocation.y || packet.a.y > buildingLocation.y + building.box!!.dimensions.y) return@registerChannelRead true
            if (packet.a.z < buildingLocation.z || packet.a.z > buildingLocation.z + building.box!!.dimensions.z) return@registerChannelRead true
            if (packet.block.material != net.minecraft.server.v1_12_R1.Material.AIR) return@registerChannelRead true

            return@registerChannelRead false
        })
    }

    fun show(player: Player) {
        if (building.allocation == null) building.allocate(buildingLocation)
        building.show(player)
    }

    fun startBuilding() {
        updateNextBlock()
        sendNextBlock()
        app.getUser(owner).currentStructure = this
    }

    fun tryPlaceBlock(item: ItemStack, location: Location): Boolean {
        updateNextBlock()
        if (buildingCompleted) return false

        if (item.getType() != nextBlock.type) return false
        if (item.getData().data != nextBlock.data) return false
        if (location.blockX != nextBlockLocation.x.toInt() ||
            location.blockY != nextBlockLocation.y.toInt() ||
            location.blockZ != nextBlockLocation.z.toInt()) return false

        placeNextBlock()
        return true
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
        if (buildingCompleted) return
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
                Anime.bigTitle(Bukkit.getPlayer(owner), "§eПостройка завершена!")
                Glow.animate(Bukkit.getPlayer(owner), 1.0, GlowColor.GREEN)
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