package me.slavita.construction.world.structure.client

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.utils.extensions.BlocksExtensions.add
import me.slavita.construction.utils.extensions.BlocksExtensions.minus
import me.slavita.construction.utils.extensions.BlocksExtensions.toLocation
import me.slavita.construction.world.BlockProperties
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.structure.Structure
import net.minecraft.server.v1_12_R1.EnumHand
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

class ClientStructure(val world: GameWorld, val structure: Structure, val owner: Player, val allocation: Location) {
    private var currentBlock: BlockProperties? = null
    private val sender = ClientSender(owner)
    private val info = ClientStructureInfo(
        owner,
        structure,
        1500,
        sender
    )

    fun updateColor() {
        if (!info.isBuilding()) return
        info.sendActiveColor()
    }

    fun startBuilding() {
        info.startBuilding()
        updateColor()
        currentBlock = structure.firstBlock!!
        sender.sendBlock(currentBlock!!, allocation, info.hasNextBlock)

        ConnectionUtil.registerReader(owner.uniqueId) { packet ->
            if (info.state != ClientStructureState.BUILDING ||
                        packet !is PacketPlayInUseItem ||
                        packet.c != EnumHand.MAIN_HAND) return@registerReader

            val clickedBlock = packet.a.toLocation(world.map.world)
                .block.getRelative(BlockFace.valueOf(packet.b.name)).location.subtract(allocation)

            if (clickedBlock.blockX != currentBlock!!.position.x ||
                clickedBlock.blockY != currentBlock!!.position.y ||
                clickedBlock.blockZ != currentBlock!!.position.z) return@registerReader

            if (info.validateBlockPlace() != BlockPlaceStatus.SUCCESS) return@registerReader

            placeCurrentBlock()
        }

        ConnectionUtil.registerWriter(owner.uniqueId) { packet ->
            return@registerWriter !(packet !is PacketPlayOutBlockChange
                    || packet.block.material != Material.AIR
                    || !structure.contains((packet.a - allocation).add(structure.properties.box.min)))
        }
    }

    fun placeCurrentBlock() {
        if (!info.isBuilding()) return
        world.placeFakeBlock(owner, currentBlock!!.withOffset(allocation))
        currentBlock = currentBlock!!.nextBlock
        info.blockPlaced()
        if (currentBlock == null) {
            info.finishBuilding()
            sender.sendFinish()
            return
        }
        info.validateNextMaterial()
        updateColor()
        sender.sendBlock(currentBlock!!, allocation, info.hasNextBlock)
    }
}