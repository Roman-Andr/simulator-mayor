package me.slavita.construction.action

import me.slavita.construction.app
import me.slavita.construction.player.User
import java.util.UUID
import kotlin.reflect.KClass

abstract class CooldownCommand(open val user: User, open val cooldown: Long) : Command {
    companion object {
        private val playerCooldown = HashMap<UUID, HashMap<KClass<CooldownCommand>, Long>>()
    }

    protected abstract fun execute()

    override fun tryExecute(ignore: Boolean): Long {
        val lastTime = playerCooldown.getOrPut(user.player.uniqueId) { HashMap() }.getOrDefault(javaClass.kotlin, 0)
        if (app.pass - lastTime >= cooldown || ignore) {
            playerCooldown[user.player.uniqueId]!![javaClass.kotlin] = app.pass
            execute()
        }
        return app.pass - lastTime - cooldown
    }
}

