package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.common.utils.LoadingState
import me.slavita.construction.listener.LoadUserEvent
import me.slavita.construction.protocol.GetUserPackage
import me.slavita.construction.utils.*
import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object UserLoader : IRegistrable {
    private val failedLoad = hashSetOf<Player>()

    override fun register() {
        runTimerAsync(0, (app.waitResponseTime + 1) * 20L) {
            failedLoad.forEach {
                tryLoadUser(it)
            }
        }
    }

    fun tryLoadUser(player: Player) = runAsync {
        if (!player.isOnline) return@runAsync
        if (player.userOrNull != null) {
            failedLoad.add(player)
            return@runAsync
        }

        val uuid = player.uniqueId
        try {
            log("try load user")
            player.sendLoadingState(LoadingState.TRY_GET)
            LoadUserEvent(cacheUser(uuid)).callEvent()
            failedLoad.remove(player)
        } catch (e: TimeoutException) {
            log("Load timeout")
            player.sendLoadingState(LoadingState.RETRY)
            if (!failedLoad.contains(player)) failedLoad.add(player)
        }
    }

    private fun cacheUser(uuid: UUID): User {
        val raw = getRawUser(uuid)
        val user = User(uuid).apply { initialize(raw) }
        log("${user.player.name} initialized")
        app.users[uuid] = user
        return user
    }

    private fun getRawUser(uuid: UUID) =
        socket.writeAndAwaitResponse<GetUserPackage>(GetUserPackage(uuid.toString()))[app.waitResponseTime, TimeUnit.SECONDS].data
}
