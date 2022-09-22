package me.slavita.construction.world.structure

import me.func.mod.Anime
import me.func.mod.conversation.ModTransfer
import me.func.mod.reactive.ReactiveProgress
import me.func.mod.ui.Glow
import me.func.mod.util.after
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.Tricolor
import me.func.protocol.math.Position
import me.slavita.construction.connection.ConnectionUtil.registerReader
import me.slavita.construction.connection.ConnectionUtil.registerWriter
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.utils.Colors
import me.slavita.construction.utils.extensions.BlocksExtensions.add
import me.slavita.construction.utils.extensions.BlocksExtensions.minus
import me.slavita.construction.utils.extensions.BlocksExtensions.toLocation
import me.slavita.construction.utils.extensions.Extensions.swapItems
import me.slavita.construction.world.BlockProperties
import me.slavita.construction.world.GameWorld
import net.minecraft.server.v1_12_R1.EnumHand
import net.minecraft.server.v1_12_R1.Material
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.inventory.PlayerInventory

class ClientStructure(val world: GameWorld, val structure: Structure, val owner: Player, val allocation: Location) {
    private var state = ClientStructureState.NOT_STARTED
    private var currentBlock: BlockProperties? = null
    private var blocksPlaced = 0
    private var progressBar = ReactiveProgress()
    private var hasBlock = true

    fun updateColor() {
        if (state != ClientStructureState.FINISHED) {
            var color = Colors.GREEN
            after (1) {
                owner.inventory.itemInMainHand.apply {
                    if (getType() != currentBlock!!.type ||
                        getData().data != currentBlock!!.data
                    ) {
                        color = Colors.RED
                    }
                }

                ModTransfer()
                    .integer(color.red)
                    .integer(color.green)
                    .integer(color.blue)
                    .double(color.alpha)
                    .send("structure:changeColor", owner)
            }
        }
    }

    fun startBuilding() {
        state = ClientStructureState.BUILDING
        currentBlock = structure.firstBlock!!
        updateColor()
        sendCurrentBlock()
        progressBar = ReactiveProgress.builder()
            .position(Position.BOTTOM)
            .offsetY(24.0)
            .hideOnTab(true)
            .color(Tricolor(36, 175, 255))
            .build().apply {
                text = "§aПоставлено блоков: §b0/0"
                after(1) {
                    progressBar.send(owner)
                    progress = 0.0
                }
            }

        registerReader(owner.uniqueId) { packet ->
            if (state != ClientStructureState.BUILDING) return@registerReader
            if (packet !is PacketPlayInUseItem) return@registerReader
            if (packet.c != EnumHand.MAIN_HAND) return@registerReader

            val clickedBlock = packet.a.toLocation(world.map.world)
                .block.getRelative(BlockFace.valueOf(packet.b.name)).location.subtract(allocation)

            if (clickedBlock.blockX != currentBlock!!.position.x ||
                clickedBlock.blockY != currentBlock!!.position.y ||
                clickedBlock.blockZ != currentBlock!!.position.z) return@registerReader

            owner.inventory.itemInMainHand.apply {
                if (getType() != currentBlock!!.type ||
                    getData().data != currentBlock!!.data) {
                    Glow.animate(owner, 0.4, GlowColor.RED)
                    Anime.killboardMessage(owner, "§cНеверный блок")
                    return@registerReader
                } else {
                    setAmount(getAmount() - 1)
                }
            }

            placeCurrentBlock()

            hasBlock = false
            (0..35).forEach {
                val item = owner.inventory.getItem(it)
                if (item != null) {
                    if (item.getType() == currentBlock?.type && item.getData().data == currentBlock?.data) {
                        (owner.inventory as PlayerInventory).apply {
                            heldItemSlot = 0
                            swapItems(0, it)
                            hasBlock = true
                        }
                    }
                }
            }
            if (!hasBlock) {
                Anime.killboardMessage(owner, "§6В инвентаре нет следующего материала")
                updateColor()
                sendCurrentBlock()
            }
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
        progressBar.progress = blocksPlaced.toDouble() / structure.getBlocksCount().toDouble()
        progressBar.text = "§aПоставлено блоков: §b$blocksPlaced/${structure.getBlocksCount()}"
        if (currentBlock == null) {
            state = ClientStructureState.FINISHED
            sendFinish()
            progressBar.delete(setOf(owner))
        }
        sendCurrentBlock()
    }

    private fun sendCurrentBlock() {
        if (state != ClientStructureState.BUILDING) return
        val position = currentBlock!!.withOffset(allocation).position
        ModTransfer()
            .double(position.x.toDouble())
            .double(position.y.toDouble())
            .double(position.z.toDouble())
            .integer(currentBlock!!.type.id)
            .byte(currentBlock!!.data)
            .boolean(hasBlock)
            .send("structure:update", owner)
    }

    private fun sendFinish() {
        Glow.animate(owner, 1.5, GlowColor.GREEN)
        Anime.itemTitle(owner, ItemIcons.get("other", "access"), "Постройка завершена", "Отличная работа", 1.5)
        ModTransfer()
            .send("structure:completed", owner)
    }
}