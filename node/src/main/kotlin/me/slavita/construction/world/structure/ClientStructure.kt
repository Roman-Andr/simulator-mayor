package me.slavita.construction.world.structure

import me.func.mod.Anime
import me.func.mod.conversation.ModTransfer
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.connection.ConnectionUtil.registerReader
import me.slavita.construction.connection.ConnectionUtil.registerWriter
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.utils.extensions.BlocksExtensions.add
import me.slavita.construction.utils.extensions.BlocksExtensions.minus
import me.slavita.construction.utils.extensions.BlocksExtensions.toLocation
import me.slavita.construction.world.BlockProperties
import me.slavita.construction.world.GameWorld
import net.minecraft.server.v1_12_R1.*
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
        sendCurrentBlock()

        registerReader(owner.uniqueId) { packet ->
            if (state != ClientStructureState.BUILDING) return@registerReader
            if (packet !is PacketPlayInUseItem) return@registerReader
            if (packet.c != EnumHand.MAIN_HAND) return@registerReader

            val clickedBlock = packet.a.toLocation(world.map.world)
                .block.getRelative(BlockFace.valueOf(packet.b.name)).location.subtract(allocation)

            if (clickedBlock.blockX != currentBlock!!.position.x ||
                clickedBlock.blockY != currentBlock!!.position.y ||
                clickedBlock.blockZ != currentBlock!!.position.z) return@registerReader

            if (owner.inventory.itemInMainHand.getType() != currentBlock!!.type) {
                Anime.killboardMessage(owner, "§cНеверный блок")
                return@registerReader
            }
            owner.inventory.itemInMainHand.setAmount(owner.inventory.itemInMainHand.getAmount() - 1)
            placeCurrentBlock()
        }

        registerWriter(owner.uniqueId) { packet ->
            return@registerWriter !(packet !is PacketPlayOutBlockChange
                    || packet.block.material != Material.AIR
                    || !structure.contains((packet.a - allocation).add(structure.properties.box.min)))
        }
    }

    fun placeCurrentBlock() {
        if (state != ClientStructureState.BUILDING) return

        world.placeFakeBlock(owner, currentBlock!!.withOffset(allocation))

        currentBlock = currentBlock!!.nextBlock
        blocksPlaced++
        if (currentBlock == null) {
            state = ClientStructureState.FINISHED
            sendFinish()
        }
        sendCurrentBlock()
    }

    private fun sendCurrentBlock() {
        if (state != ClientStructureState.BUILDING) return
        val position = currentBlock!!.withOffset(allocation).position
        ModTransfer()
            .integer(position.x)
            .integer(position.y)
            .integer(position.z)
            .integer(currentBlock!!.type.id)
            .integer(structure.getBlocksCount() - blocksPlaced)
            .byte(currentBlock!!.data)
            .send("structure:next", owner)
    }

    private fun sendFinish() {
        Glow.animate(owner, 1.5, GlowColor.GREEN)
        Anime.itemTitle(owner, ItemIcons.get("other", "access"), "Постройка завершена", "Отличная работа", 1.5)
        ModTransfer()
            .send("structure:completed", owner)
    }
}