package me.slavita.construction.city.utils

import me.func.mod.reactive.ReactiveProgress
import me.func.protocol.data.color.GlowColor
import me.func.protocol.math.Position
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.ticksToTime
import me.slavita.construction.ui.Formatter.toTime
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.runTimerAsync
import org.bukkit.entity.Player

class AnimeTimer(val player: Player, val onFinish: () -> Unit) {
    companion object {
        val timers = hashSetOf<AnimeTimer>()
    }

    var startedAt = 0L
    var duration = 0L

    val bar = ReactiveProgress.builder()
        .position(Position.BOTTOM)
        .offsetY(36.0)
        .hideOnTab(false)
        .color(GlowColor.BLUE)
        .build()

    fun start(duration: Long) {
        this.duration = duration
        startedAt = app.pass
        timers.add(this)

        bar.apply {
            update()
            send(player)
            progress = 0.0
        }
    }

    fun update() {
        val delta = app.pass - startedAt

        if (delta < duration) {
            bar.apply {
                progress = delta.toDouble() / duration.toDouble()
                text = "Осталось: ${(duration - delta).ticksToTime()}"
            }
            return
        }

        onFinish()
        timers.remove(this)
    }

    fun stop() {
        timers.remove(this)
        bar.delete(setOf(player))
    }
}
