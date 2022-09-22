package me.slavita.construction.world.structure.client

import me.func.mod.Anime
import me.func.mod.conversation.ModTransfer
import me.func.mod.reactive.ReactiveProgress
import me.func.mod.ui.Glow
import me.func.mod.util.after
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.Tricolor
import me.func.protocol.math.Position
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.utils.SpecialColor
import me.slavita.construction.utils.Cooldown
import me.slavita.construction.utils.extensions.Extensions.swapItems
import me.slavita.construction.world.BlockProperties
import me.slavita.construction.world.structure.Structure
import org.bukkit.entity.Player
import org.bukkit.inventory.PlayerInventory

data class ClientStructureInfo(
    val owner: Player,
    val structure: Structure,
    val cooldownTime: Long,
    val sender: ClientSender
) {
    var state = ClientStructureState.NOT_STARTED
    var hasNextBlock = true
    private val blocksTotal = structure.getBlocksCount()
    private val cooldown = Cooldown(cooldownTime, owner)
    private var progressBar = ReactiveProgress()
    private var blocksPlaced = 0
    private var currentBlock: BlockProperties? = null

    init {
        progressBar = ReactiveProgress.builder()
            .position(Position.BOTTOM)
            .offsetY(24.0)
            .hideOnTab(true)
            .color(Tricolor(36, 175, 255))
            .text("§aПоставлено блоков: §b0/0")
            .build()
    }

    fun startBuilding() {
        state = ClientStructureState.BUILDING
        showProgress()
        currentBlock = structure.firstBlock!!
    }

    fun isBuilding() = state == ClientStructureState.BUILDING

    fun validateBlockPlace(): BlockPlaceStatus {
        owner.inventory.itemInMainHand.apply {
            if (getType() != currentBlock!!.type ||
                getData().data != currentBlock!!.data) {
                Glow.animate(owner, 0.4, GlowColor.RED)
                Anime.killboardMessage(owner, "§cНеверный блок")
                return BlockPlaceStatus.WRONG_BLOCK
            } else {
                if (!cooldown.isExpired()) {
                    Glow.animate(owner, 0.4, GlowColor.RED)
                    Anime.killboardMessage(owner, "§cВы сможете поставить блок через §b${cooldown.getTimeLast()}")
                    return BlockPlaceStatus.COOLDOWN
                }
                setAmount(getAmount() - 1)
                return BlockPlaceStatus.SUCCESS
            }
        }
    }

    fun sendActiveColor() {
        var color = SpecialColor.GREEN
        after (1) {
            owner.inventory.itemInMainHand.apply {
                if (getType() != currentBlock!!.type ||
                    getData().data != currentBlock!!.data
                ) {
                    color = SpecialColor.RED
                }
                else {
                    if (!cooldown.isExpired()) color = SpecialColor.GOLD
                }
            }

            sender.sendColor(color)
        }
    }

    fun blockPlaced() {
        blocksPlaced++
        cooldown.start()
        progressBar.apply {
            progress = blocksPlaced.toDouble() / blocksTotal.toDouble()
            text = "§aПоставлено блоков: §b$blocksPlaced/$blocksTotal"
        }
    }

    fun finishBuilding() {
        state = ClientStructureState.FINISHED
        progressBar.delete(setOf(owner))
        Glow.animate(owner, 1.5, GlowColor.GREEN)
        Anime.itemTitle(owner, ItemIcons.get("other", "access"), "Постройка завершена", "Отличная работа", 1.5)
    }

    fun validateNextMaterial() {
        hasNextBlock = false
        (0..35).forEach {
            val item = owner.inventory.getItem(it)
            if (item != null) {
                if (item.getType() == currentBlock?.type && item.getData().data == currentBlock?.data) {
                    (owner.inventory as PlayerInventory).apply {
                        heldItemSlot = 0
                        swapItems(0, it)
                        hasNextBlock = true
                    }
                }
            }
        }
        if (!hasNextBlock) {
            Glow.animate(owner, 0.4, GlowColor.GOLD)
            Anime.killboardMessage(owner, "§6В инвентаре нет следующего материала")
        }
    }

    private fun showProgress() {
        after(1) {
            progressBar.apply {
                send(owner)
                progress = 0.0
            }
        }
    }
}