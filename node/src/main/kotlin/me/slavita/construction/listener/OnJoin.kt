package me.slavita.construction.listener

import me.func.mod.util.after
import me.slavita.construction.app
import me.slavita.construction.prepare.*
import me.slavita.construction.utils.listener
import me.slavita.construction.utils.user
import me.slavita.construction.utils.userOrNull
import org.bukkit.event.EventPriority
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent

object OnJoin {
    init {
        listener<PlayerJoinEvent> event@{
            player.userOrNull?.apply {
                player = this@event.player
                initialized = true
            }

            after(2) {
                if (app.getUserOrNull(player.uniqueId) == null) {
                    player.kickPlayer("Не удалось прогрузить вашу статистику, перезайдите")
                    return@after
                }
                player.user.run {
                    listOf(
                        UIPrepare,
                        TagsPrepare,
                        CitiesPrepare,
                        PlayerWorldPrepare,
                        TabPrepare,
                        ConnectionPrepare,
                        PermissionsPrepare,
                        ItemCallbacksPrepare,
                        BankAccountPrepare,
                        GuidePrepare,
                        StoragePrepare,
                        AbilityPrepare,
                        DailyRewardsPrepare,
                        ShowcasePrepare,
                    ).forEach { it.prepare(this) }
                }
            }
        }

        listener<AsyncPlayerPreLoginEvent>(EventPriority.LOWEST) {
//            val now = System.currentTimeMillis()
//
////            if (now < app.started + 10000) {
////                disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Сервер запускается...")
////                loginResult = AsyncPlayerPreLoginEvent.Result.KICK_OTHER
////                return@listener
////            }
//
//            val lock = Lock.getLock("construction-$uniqueId", TimeUnit.SECONDS)
//
//            if (lock in 1..59) {
//                disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "Ваши данные сохраняются...")
//            }
        }
    }
}
