package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.listener.OnActions
import me.slavita.construction.player.User
import me.slavita.construction.utils.nextTick
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.runTimer
import org.bukkit.GameMode

object PlayerWorldPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            user.data.inventory.forEach {
                player.inventory.setItem(it.slot, it.createItemStack(it.amount))
            }
            OnActions.inZone[user.player.uniqueId] = false
            OnActions.storageEntered[user.player.uniqueId] = false

            app.mainWorld.glows.forEach { it.send(player) }

            nextTick {
                player.apply {
                    gameMode = GameMode.ADVENTURE
                    walkSpeed = data.speed
                    teleport(currentCity.getSpawn())
                }
            }
        }
    }
}
