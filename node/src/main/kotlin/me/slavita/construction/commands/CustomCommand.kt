package me.slavita.construction.commands

import me.func.mod.Anime
import me.slavita.construction.app
import org.bukkit.entity.Player

class CustomCommand(
    val cooldown: Long,
    val executeCommand: Command
) {
    private val timeLast: Long
        get() {
            val time = (lastTime + cooldown) - app.pass
            return if (time > 0) time else 0
        }
    val canUse: Boolean
        get() = timeLast == 0L
    private var lastTime: Long = 0

    init {
        lastTime = app.pass - cooldown
    }

    fun execute(player: Player) {
        println(timeLast)
        if (app.pass - lastTime > cooldown) {
            lastTime = app.pass
            executeCommand.execute(player)
        }
    }
}