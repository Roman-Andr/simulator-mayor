package me.slavita.construction.world

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.connection.ConnectionUtil.registerReader
import me.slavita.construction.connection.ConnectionUtil.registerWriter
import net.minecraft.server.v1_12_R1.BlockPosition
import net.minecraft.server.v1_12_R1.EnumHand
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

class ClientStructure(val world: GameWorld, val structure: Structure, val owner: Player, val allocation: Location) {
    private var state = ClientStructureState.NOT_STARTED
    private var currentBlock: BlockProperties? = null
    private var blocksPlaced = 0

    fun startBuilding() {
        state = ClientStructureState.BUILDING
        currentBlock = structure.firstBlock!!

        registerReader(owner.uniqueId) { packet ->
            if (state != ClientStructureState.BUILDING) return@registerReader
            if (packet !is PacketPlayInUseItem) return@registerReader
            if (packet.c != EnumHand.MAIN_HAND) return@registerReader

            val clickedBlock = Location(
                world.map.world,
                packet.a.x.toDouble(),
                packet.a.y.toDouble(),
                packet.a.z.toDouble()
            ).block.getRelative(BlockFace.valueOf(packet.b.name)).location

            if (clickedBlock.blockX != currentBlock!!.position.x ||
                clickedBlock.blockY != currentBlock!!.position.y ||
                clickedBlock.blockZ != currentBlock!!.position.z) return@registerReader

            placeCurrentBlock()
        }

        registerWriter(owner.uniqueId) { context, packet ->
            if (packet !is PacketPlayOutBlockChange) return@registerWriter
            if (packet.block.material != Material.AIR) return@registerWriter
            if (!structure.contains(BlockPosition(packet.a.x - allocation.x, packet.a.y - allocation.y, packet.a.z - allocation.z))) return@registerWriter

            context.cancelled = true
        }
    }

    fun placeCurrentBlock() {
        if (state != ClientStructureState.BUILDING) return

        sendCurrentBlock()
        world.placeFakeBlock(owner, currentBlock!!)

        currentBlock = currentBlock!!.nextBlock
        if (currentBlock == null) state = ClientStructureState.BUILDING
    }

    private fun sendCurrentBlock() {
        if (state != ClientStructureState.BUILDING) return
        ModTransfer()
            .integer(currentBlock!!.position.x)
            .integer(currentBlock!!.position.y)
            .integer(currentBlock!!.position.z)
            .integer(currentBlock!!.type.id)
            .integer(structure.getBlocksCount() - blocksPlaced)
            .byte(currentBlock!!.data)
            .send("structure:next", owner)
    }
}