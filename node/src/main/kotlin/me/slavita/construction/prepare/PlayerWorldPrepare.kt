package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.utils.nextTick
import org.bukkit.GameMode

object PlayerWorldPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            player.inventory.clear()
            user.data.inventory.forEach {
                player.inventory.setItem(it.slot, it.createItemStack(it.amount))
            }

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
