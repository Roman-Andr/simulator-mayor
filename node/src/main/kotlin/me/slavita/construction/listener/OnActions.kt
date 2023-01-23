package me.slavita.construction.listener

import me.slavita.construction.ui.HumanizableValues
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.listener
import me.slavita.construction.utils.user
import me.slavita.construction.utils.userOrNull
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerMoveEvent
import java.util.*

object OnActions {
    val inZone = hashMapOf<UUID, Boolean>()
    var storageEntered = hashMapOf<UUID, Boolean>()

    init {
        listener<PlayerDropItemEvent> {
            val user = player.userOrNull ?: return@listener
            if (!user.data.blocksStorage.inBox() || itemDrop.itemStack.getType() == Material.CLAY_BALL) {
                isCancelled = true
                return@listener
            }

            val toAdd = user.blocksStorage.addItem(drop.itemStack)
            player.accept(
                "Вы положили ${GOLD}${
                    LanguageHelper.getItemDisplayName(
                        drop.itemStack,
                        player
                    )
                }: ${drop.itemStack.getAmount() - toAdd} ${
                    HumanizableValues.BLOCK.get(drop.itemStack.getAmount())
                }"
            )
            if (toAdd != 0) player.inventory.addItem(drop.itemStack.apply { setAmount(toAdd) })

            drop.remove()
        }

        listener<PlayerMoveEvent> {isCancelled = true
            if (player.userOrNull == null) isCancelled = true
            else if (player.user.currentFreelance != null && !player.user.freelanceCell.box.contains(to)) isCancelled = true
            else player.user.updatePosition()
        }
    }
}
