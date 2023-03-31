package me.slavita.construction.region

import me.func.mod.conversation.ModTransfer
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.common.utils.STRUCTURE_BLOCK_CHANNEL
import me.slavita.construction.common.utils.STRUCTURE_HIDE_CHANNEL
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.swapItems
import org.bukkit.inventory.ItemStack

open class ClientStructureBase(options: StructureOptions, cell: Cell, saveFakeBlocks: Boolean) : BuildingStructure(options, cell, saveFakeBlocks) {

    override fun enter() {
        super.enter()
        sendBlock()
    }

    override fun blockPlaced() {
        super.blockPlaced()
        sendBlock()
    }

    override fun leave() {
        super.leave()
        ModTransfer().send(STRUCTURE_HIDE_CHANNEL, user.player)
    }

    fun tryPlaceBlock() {
        user.player.inventory.itemInMainHand.apply {
            if (!currentBlock.equalsItem(this)) {
                Glow.animate(user.player, 0.2, GlowColor.RED)
                user.player.deny("Неверный блок")
                return
            }

            setAmount(getAmount() - 1)
            placeCurrentBlock()
            user.receiveRandomReward()

            putNextInHands()
        }
    }

    fun putNextInHands() {
        var hasNext = false
        user.player.inventory.apply {
            if (currentBlock.equalsItem(itemInMainHand)) return

            storageContents.forEachIndexed { index, item ->
                if (!currentBlock.equalsItem(item)) return@forEachIndexed
                hasNext = true
                swapItems(heldItemSlot, index)
            }

            if (!hasNext) {
                Glow.animate(user.player, 0.2, GlowColor.ORANGE)
                user.player.deny("В инвентаре нет нужного материала")
            }
        }
    }

    @Suppress("DEPRECATION")
    fun sendBlock() = currentBlock.withOffset(allocation).position.run {
        ModTransfer()
            .double(x.toDouble())
            .double(y.toDouble())
            .double(z.toDouble())
            .item(ItemStack(currentBlock.type, 1, 1, currentBlock.data))
            .send(STRUCTURE_BLOCK_CHANNEL, user.player)
    }
}
