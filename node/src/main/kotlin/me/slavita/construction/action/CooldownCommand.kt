package me.slavita.construction.action

import me.slavita.construction.app
import org.bukkit.entity.Player
import java.util.*
import kotlin.reflect.KClass

abstract class CooldownCommand(val player: Player, private var cooldown: Long) : Command {
    companion object {
        private val playerCooldown = HashMap<UUID, HashMap<KClass<CooldownCommand>, Long>>()
    }

    protected abstract fun execute()

    override fun tryExecute(ignore: Boolean): Long {
        val lastTime = playerCooldown.getOrPut(player.uniqueId) { HashMap() }.getOrDefault(javaClass.kotlin, 0)
        if (app.pass - lastTime >= cooldown || ignore) {
            playerCooldown[player.uniqueId]!![javaClass.kotlin] = app.pass
            execute()
        }
        return app.pass - lastTime - cooldown
    }
}
