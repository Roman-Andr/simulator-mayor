package me.slavita.construction.listener

import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.ui.HumanizableValues.BLOCK
import me.slavita.construction.utils.*
import me.slavita.construction.utils.language.LanguageHelper
import org.bukkit.ChatColor.GOLD
import org.bukkit.Material
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerMoveEvent
import java.util.UUID

object OnActions : IRegistrable {
    val inZone = hashMapOf<UUID, Boolean>()
    var storageEntered = hashMapOf<UUID, Boolean>()

    override fun register() {
        listener<PlayerDropItemEvent> {
            val user = player.user
            if (itemDrop.itemStack.getType() == Material.CLAY_BALL) {
                isCancelled = true
                return@listener
            }
            if (user.inTrashZone) {
                drop.remove()
                return@listener
            }

            if (!user.data.blocksStorage.inBox()) {
                isCancelled = true
                return@listener
            }

            val toAdd = user.data.blocksStorage.addItem(drop.itemStack)
            val depositBlocks = drop.itemStack.getAmount() - toAdd
            if (depositBlocks > 0) {
                player.accept(
                    "Вы положили ${GOLD}${
                        LanguageHelper.getItemDisplayName(
                            drop.itemStack,
                            player
                        )
                    }: ${BLOCK.get(depositBlocks)}"
                )
            } else {
                player.deny("Нехватает места на складе!")
            }
            if (toAdd != 0) {
                player.inventory.addItem(drop.itemStack.apply { setAmount(toAdd) })
            }

            drop.remove()
        }

        listener<PlayerMoveEvent> {
            if (player.userOrNull == null) isCancelled = true
            else if (player.user.currentFreelance != null && !player.user.freelanceCell.box.contains(to)) isCancelled =
                true
            else player.user.updatePosition()
        }
    }
}
