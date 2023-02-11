package me.slavita.construction.structure

import me.func.mod.conversation.ModTransfer
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.common.utils.STRUCTURE_BLOCK_CHANNEL
import me.slavita.construction.common.utils.STRUCTURE_HIDE_CHANNEL
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.reward.ReputationReward
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.swapItems
import me.slavita.construction.world.StructureBlock
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class ClientStructure(
    structure: Structure,
    cell: CityCell,
) : BuildingStructure(structure, cell) {
    @Suppress("DEPRECATION")
    private fun sendBlock(block: StructureBlock, offset: Location) {
        val position = block.withOffset(offset).position
        ModTransfer()
            .double(position.x.toDouble())
            .double(position.y.toDouble())
            .double(position.z.toDouble())
            .item(ItemStack(block.type, 1, 1, block.data))
            .send(STRUCTURE_BLOCK_CHANNEL, owner.player)
    }

    private fun sendHide() {
        ModTransfer().send(STRUCTURE_HIDE_CHANNEL, owner.player)
    }

    override fun enterBuilding() {
        continueBuilding()
    }

    override fun onFinish() {}

    override fun onShow() {
        sendBlock(currentBlock!!, allocation)
    }

    override fun onHide() {
        sendHide()
    }

    override fun blockPlaced() {
        if (currentBlock == null) return
        if (!hidden) sendBlock(currentBlock!!, allocation)
    }

    override fun getBannerInfo(): List<Pair<String, Double>> {
        return listOf(
            Pair("Информация об постройке", 0.7),
            Pair("Название: ${structure.name}", 0.7),
            Pair("", 0.5),
            Pair("Прогресс: $blocksPlaced из ${structure.blocksCount} блоков", 0.5)
        )
    }

    fun tryPlaceBlock() {
        owner.data.run {
            when (Random.nextInt(100)) {
                in 0..5 ->
                    ReputationReward(
                        reputation / 100 +
                            Random.nextInt(1, 10) * level
                    ).getReward(owner)

                in 0..20 ->
                    MoneyReward(money / 100).getReward(owner)
            }
        }

        owner.player.inventory.itemInMainHand.apply {
            if (currentBlock == null) return
            if (!currentBlock!!.equalsItem(this)) {
                Glow.animate(owner.player, 0.2, GlowColor.RED)
                owner.player.deny("Неверный блок")
                return
            }

            setAmount(getAmount() - 1)
            placeCurrentBlock()
            var hasNext = false

            if (currentBlock == null || currentBlock!!.equalsItem(this)) return

            owner.player.inventory.apply {
                storageContents.forEachIndexed { index, item ->
                    if (!currentBlock!!.equalsItem(item)) return@forEachIndexed
                    hasNext = true
                    swapItems(heldItemSlot, index)
                }

                if (!hasNext) {
                    Glow.animate(owner.player, 0.2, GlowColor.ORANGE)
                    owner.player.deny("В инвентаре нет нужного материала")
                }
                return
            }
        }
    }
}
