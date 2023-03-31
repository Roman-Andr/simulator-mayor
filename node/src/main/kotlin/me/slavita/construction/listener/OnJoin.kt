package me.slavita.construction.listener

import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.player.UserLoader
import me.slavita.construction.utils.coroutine
import me.slavita.construction.utils.listener
import me.slavita.construction.utils.nextTick
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent

object OnJoin : IRegistrable {
    override fun register() {
        listener<PlayerJoinEvent> event@{
            player.inventory.clear()

            nextTick {
                coroutine { UserLoader.tryLoadUser(player) }
            }
        }

        listener<AsyncPlayerPreLoginEvent>(EventPriority.LOWEST) {
//            val now = System.currentTimeMillis()
//
// //            if (now < app.started + 10000) {
// //                disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Сервер запускается...")
// //                loginResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER
// //                return@listener
// //            }
//
//            val lock = Lock.getLock("construction-$uniqueId", TimeUnit.SECONDS)
//
//            if (lock in 1..59) {
//                disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Ваши данные сохраняются...")
//            }
        }
    }
}
