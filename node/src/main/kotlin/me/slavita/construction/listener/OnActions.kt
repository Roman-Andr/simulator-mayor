package me.slavita.construction.listener

import me.func.mod.conversation.ModTransfer
import me.slavita.construction.action.command.menu.city.BuyCityConfirm
import me.slavita.construction.action.command.menu.project.ChoiceStructureGroup
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.ui.HumanizableValues
import me.slavita.construction.utils.PlayerExtensions.accept
import me.slavita.construction.utils.listener
import me.slavita.construction.utils.user
import me.slavita.construction.utils.userOrNull
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerMoveEvent

object OnActions {
    val inZone = hashMapOf<Player, Boolean>()
    var storageEntered = hashMapOf<Player, Boolean>()

    init {
        listener<PlayerDropItemEvent> {
            val user = player.userOrNull ?: return@listener
            if (!user.blocksStorage.inBox() || itemDrop.itemStack.getType() == Material.CLAY_BALL) {
                isCancelled = true
                return@listener
            }

            player.accept(
                "Вы положили ${drop.itemStack.getAmount()} ${
                    HumanizableValues.BLOCK.get(
                        drop.itemStack.getAmount()
                    )
                }"
            )
            user.blocksStorage.addItem(drop.itemStack)
            drop.remove()
        }

        listener<PlayerMoveEvent> {
            player.userOrNull?.updatePosition()
        }
    }
}
