package me.slavita.construction.structure.client

import implario.humanize.Humanize
import me.func.mod.Anime
import me.func.mod.ui.Glow
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.connection.ConnectionUtil
import me.slavita.construction.structure.BuildingStructure
import me.slavita.construction.structure.Structure
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.utils.extensions.BlocksExtensions.minus
import me.slavita.construction.utils.extensions.BlocksExtensions.toLocation
import me.slavita.construction.utils.extensions.PlayerExtensions.swapItems
import me.slavita.construction.world.GameWorld
import net.minecraft.server.v1_12_R1.*
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

class ClientStructure(
    world: GameWorld,
    structure: Structure,
    owner: Player,
    allocation: Location
) : BuildingStructure(world, structure, owner, allocation) {

    override fun enterBuilding() {
        ConnectionUtil.registerReader(owner.uniqueId) { packet ->
            if (packet !is PacketPlayInUseItem || state != StructureState.BUILDING || packet.c != EnumHand.MAIN_HAND) return@registerReader

            val clickedBlock = packet.a.toLocation(world.map.world).block
            val relativeBlock = clickedBlock.getRelative(BlockFace.valueOf(packet.b.name)).location

            if (currentBlock!!.withOffset(allocation).equalsLocation(relativeBlock)) tryPlaceBlock()
        }

        ConnectionUtil.registerWriter(owner.uniqueId) { packet ->
            if (packet !is PacketPlayOutBlockChange) return@registerWriter
            if (packet.block.material != Material.AIR) return@registerWriter

            if (structure.contains(packet.a - allocation)) packet.a = BlockPosition(0, 0, 0)
        }
    }

    override fun placeCurrentBlock() {
        if (state != StructureState.BUILDING) return

        world.placeFakeBlock(owner, currentBlock!!.withOffset(allocation))
        currentBlock = structure.getNextBlock(currentBlock!!.position)
        cooldown.start { sender.sendCooldownExpired() }

        blocksPlaced++
        progressBar.update(blocksPlaced)

        if (currentBlock == null) {
            finishBuilding()
            return
        }
        sender.sendBlock(currentBlock!!, allocation)
    }

    private fun tryPlaceBlock() {
        owner.inventory.itemInMainHand.apply {
            if (!currentBlock!!.equalsItem(this)) {
                Glow.animate(owner, 0.2, GlowColor.RED)
                Anime.killboardMessage(owner, "§cНеверный блок")
                return
            }

            if (!cooldown.isExpired()) {
                Anime.killboardMessage(owner, "§cВы сможете поставить блок через §b${cooldown.timeLeft()} ${
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
                    Anime.killboardMessage(owner, "§6В инвентаре нет нужного материала")
                }
                return
            }
        }
    }

    private fun finishBuilding() {
        state = StructureState.FINISHED
        progressBar.hide()
        sender.sendCompleted()
        Glow.animate(owner, 1.5, GlowColor.GREEN)
        Anime.itemTitle(owner, ItemIcons.get("other", "access"), "Постройка завершена", "Отличная работа", 1.5)
    }
}