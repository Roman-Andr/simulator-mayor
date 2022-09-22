package me.slavita.construction.utils

import me.func.mod.Anime
import me.func.protocol.data.color.GlowColor
import org.bukkit.entity.Player
import java.text.DecimalFormat

class Cooldown(
    val time: Long,
    val player: Player
) {

    private var startTime = 0L

    fun start() {
        startTime = System.currentTimeMillis()
        Anime.reload(player, time.toDouble() / 1000, "Перезарядка", Colors.GOLD.toRGB())
    }

    fun getTimeLast() : Double {
        val targetTime = time - (System.currentTimeMillis() - startTime)
        return if (targetTime > 0) (targetTime.toDouble() / 1000)
        else 0.0
    }

    fun isExpired() = (time - (System.currentTimeMillis() - startTime)) <= 0
}