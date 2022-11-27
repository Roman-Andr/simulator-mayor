package me.slavita.construction.player.events

import me.func.Lock
import me.func.mod.util.after
import me.slavita.construction.action.command.menu.project.ChoiceStructure
import me.slavita.construction.app
import me.slavita.construction.prepare.*
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.utils.listener
import me.slavita.construction.utils.user
import me.slavita.construction.utils.userOrNull
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.player.*
import java.util.concurrent.TimeUnit

object PlayerEvents {
    private val inZone = hashMapOf<Player, Boolean>()

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
                        PlayerWorldPrepare,
                        ConnectionPrepare,
                        PermissionsPrepare,
                        ItemCallbacksPrepare,
                        ShowcasePrepare,
                        BankAccountPrepare,
                        GuidePrepare,
                        StoragePrepare,
                        AbilityPrepare
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

        listener<PlayerQuitEvent> {
            Lock.lock("construction-${player.uniqueId}", 10, TimeUnit.SECONDS)
        }

        listener<PlayerDropItemEvent> {
            val user = player.user
            if (!user.blocksStorage.inBox()) {
                isCancelled = true
                return@listener
            }

            user.blocksStorage.addItem(drop.itemStack)
            drop.remove()
        }

        listener<PlayerMoveEvent> {
            player.user.run {
                if (watchableProject != null && !watchableProject!!.structure.box.contains(player.location)) {
                    watchableProject!!.onLeave()
                    watchableProject = null
                }

                currentCity.cityStructures.forEach {
                    if (it.cell.box.contains(player.location) && it.state == CityStructureState.BROKEN) {
                        it.repair()
                    }
                }

                if (watchableProject == null) {
                    currentCity.projects.forEach {
                        if (it.structure.box.contains(player.location)) {
                            watchableProject = it
                            it.onEnter()
                            return@listener
                        }
                    }

                    currentCity.cells.forEach {
                        if (it.busy || !it.box.contains(player.location)) return@forEach

                        if (inZone[player] == false) ChoiceStructure(player, it).tryExecute()
                        inZone[player] = true
                        return@listener
                    }

                    inZone[player] = false
                }
            }
        }
    }
}