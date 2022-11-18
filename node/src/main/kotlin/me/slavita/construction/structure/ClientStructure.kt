package me.slavita.construction.structure

import implario.humanize.Humanize
import me.func.mod.Anime
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.player.User
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureSender
import me.slavita.construction.utils.Cooldown
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.extensions.PlayerExtensions.swapItems
import me.slavita.construction.world.GameWorld
import org.bukkit.ChatColor.*

class ClientStructure(
    world: GameWorld,
    structure: Structure,
    owner: User,
    cell: Cell,
) : BuildingStructure(world, structure, owner, cell) {
    private val sender = StructureSender(owner.player)
    private val cooldown = Cooldown(5, owner.player)

    override fun enterBuilding() {
        Anime.createReader("structure:place") { player, _ ->
            if (player.uniqueId == owner.uuid) tryPlaceBlock()
        }
    }

    override fun onShow() {
        sender.sendBlock(currentBlock!!, allocation)
    }

    override fun onHide() {
        sender.sendHide()
    }

    override fun blockPlaced() {
        cooldown.start { if (!hidden) sender.sendCooldownExpired() }
        if (!hidden) sender.sendBlock(currentBlock!!, allocation)
    }

    override fun getBannerInfo(): List<Pair<String, Double>> {
        return listOf(
            Pair("Информация об постройке", 0.7),
            Pair("Название: ${structure.name}", 0.7),
            Pair("", 0.5),
            Pair("Прогресс: $blocksPlaced блоков из ${structure.blocksCount}", 0.5)
        )
    }

    private fun tryPlaceBlock() {
        owner.player.inventory.itemInMainHand.apply {
            if (!currentBlock!!.equalsItem(this)) {
                Glow.animate(owner.player, 0.2, GlowColor.RED)
                owner.player.killboard("${RED}Неверный блок")
                return
            }

            if (!cooldown.isExpired()) {
                owner.player.killboard(
                    "${AQUA}Вы сможете поставить блок через ${AQUA}${cooldown.timeLeft()} ${
                        Humanize.plurals(
                            "секунду",
                            "секунды",
                            "секунд",
                            cooldown.timeLeft().toInt()
                        )
                    }"
                )
                return
            }

            setAmount(getAmount() - 1)
            placeCurrentBlock()
            var hasNext = false

            if (currentBlock!!.equalsItem(this)) return

            owner.player.inventory.apply {
                storageContents.forEachIndexed { index, item ->
                    if (!currentBlock!!.equalsItem(item)) return@forEachIndexed
                    hasNext = true
                    swapItems(heldItemSlot, index)
                }

                if (!hasNext) {
                    Glow.animate(owner.player, 0.2, GlowColor.ORANGE)
                    owner.player.killboard("${WHITE}В инвентаре нет нужного материала")
                }
                return
            }
        }
    }
}