package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.listener.OnActions
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.runTimer
import org.bukkit.GameMode

object PlayerWorldPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            player.gameMode = GameMode.ADVENTURE
            player.teleport(currentCity.getSpawn())

            OnActions.inZone[user.player] = false
            OnActions.storageEntered[user.player] = false

            app.mainWorld.glows.forEach { it.send(player) }
            player.walkSpeed = data.statistics.speed

            taskId = runTimer(0, 20) {
                if (initialized && player.isOnline) data.statistics.money += income.applyBoosters(BoosterType.MONEY_BOOSTER)
            }

            showcaseTaskId = runTimer(0, 10 * 20) {
                data.showcases.forEach { showcase ->
                    showcase.updatePrices()
                }
                player.accept("Цены обновлены!")
            }
        }
    }
}