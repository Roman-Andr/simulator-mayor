package me.slavita.construction.utils

import me.func.mod.Anime
import me.func.mod.util.after
import me.slavita.construction.app
import org.bukkit.entity.Player
import java.lang.Long.max

class Cooldown(
    val duration: Long,
    val player: Player,
) {
    private var startTime = 0L

    fun start(finishAction: () -> Unit) {
        startTime = app.pass
        after(duration) {
            finishAction()
        }
        Anime.reload(player, duration / 20.0, "Перезарядка", SpecialColor.GOLD.toRGB())
    }

    fun timeLeft() : Long {
        val timeLeft = duration - (app.pass - startTime)
        return (max(timeLeft, 0) + 19) / 20
    }

    fun isExpired() = timeLeft() == 0L
}