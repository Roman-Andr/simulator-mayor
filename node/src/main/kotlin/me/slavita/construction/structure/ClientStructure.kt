package me.slavita.construction.structure

import implario.humanize.Humanize
import me.func.mod.Anime
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureSender
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.utils.Cooldown
import me.slavita.construction.utils.extensions.BlocksExtensions.toLocation
import me.slavita.construction.utils.extensions.LoggerUtils.killboard
import me.slavita.construction.utils.extensions.PlayerExtensions.swapItems
import me.slavita.construction.world.GameWorld
import net.minecraft.server.v1_12_R1.EnumHand
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem
import org.bukkit.ChatColor.*
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

class ClientStructure(
    world: GameWorld,
    structure: Structure,
    owner: Player,
    allocation: Location
) : BuildingStructure(world, structure, owner, allocation) {
    private val sender = StructureSender(owner)
    private val cooldown = Cooldown(30, owner)

    override fun enterBuilding() {
        show()

        ConnectionUtil.registerReader(owner.uniqueId) { packet ->
            if (packet !is PacketPlayInUseItem || state != StructureState.BUILDING || packet.c != EnumHand.MAIN_HAND) return@registerReader

            val clickedBlock = packet.a.toLocation(world.map.world).block
            val relativeBlock = clickedBlock.getRelative(BlockFace.valueOf(packet.b.name)).location

            if (currentBlock!!.withOffset(allocation).equalsLocation(relativeBlock)) tryPlaceBlock()
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

    override fun buildFinished() {
        Glow.animate(owner, 1.5, GlowColor.GREEN)
        Anime.itemTitle(owner, ItemIcons.get("other", "access"), "Постройка завершена", "Отличная работа", 1.5)
    }

    private fun tryPlaceBlock() {
        owner.inventory.itemInMainHand.apply {
            if (!currentBlock!!.equalsItem(this)) {
                Glow.animate(owner, 0.2, GlowColor.RED)
                owner.killboard("${RED}Неверный блок")
                return
            }

            if (!cooldown.isExpired()) {
                owner.killboard("${AQUA}Вы сможете поставить блок через ${AQUA}${cooldown.timeLeft()} ${
                    Humanize.plurals(
                        "секунду",
                        "секунды",
                        "секунд",
                        cooldown.timeLeft().toInt()
                    )}"
                )
                return
            }

            setAmount(getAmount() - 1)
            placeCurrentBlock()
            var hasNext = false

            if (currentBlock!!.equalsItem(this)) return

            owner.inventory.apply {
                storageContents.forEachIndexed { index, item ->
                    if (!currentBlock!!.equalsItem(item)) return@forEachIndexed
                    hasNext = true
                    swapItems(heldItemSlot, index)
                }

                if (!hasNext) {
                    Glow.animate(owner, 0.2, GlowColor.GOLD)
                    owner.killboard("${WHITE}В инвентаре нет нужного материала")
                }
                return
            }
        }
    }
}