package me.slavita.construction.player

import com.google.gson.GsonBuilder
import me.slavita.construction.app
import me.slavita.construction.city.City
import me.slavita.construction.city.CitySerializer
import me.slavita.construction.city.storage.BlocksStorage
import me.slavita.construction.city.storage.BlocksStorageSerializer
import me.slavita.construction.listener.LoadUserEvent
import me.slavita.construction.prepare.IRegistrable
import me.slavita.construction.project.Project
import me.slavita.construction.project.ProjectSerializer
import me.slavita.construction.protocol.GetUserPackage
import me.slavita.construction.protocol.SaveUserPackage
import me.slavita.construction.structure.*
import me.slavita.construction.utils.*
import me.slavita.construction.world.SlotItem
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object UserLoader : IRegistrable {
    private val failedLoad = hashSetOf<Player>()

    override fun register() {
        runTimerAsync(0, (app.waitResponseTime + 1) * 20L) {
            failedLoad.forEach {
                tryLoadUser(it, false)
            }
        }
    }

    fun tryLoadUser(player: Player, silent: Boolean) = runAsync {
        if (!player.isOnline) return@runAsync
        val uuid = player.uniqueId
        try {
            log("try load user")
            LoadUserEvent(cacheUser(uuid)).callEvent()
            failedLoad.remove(player)
            if (!silent) player.accept("Данные успешно загружены")
        } catch (e: TimeoutException) {
            log("user load timeout")
            player.deny("Не удалось загрузить ваши данные\nПовторная загрузка данных...")
            if (!failedLoad.contains(player)) failedLoad.add(player)
        }
    }

    private fun cacheUser(uuid: UUID): User {
        val raw = getRawUser(uuid)
        log("got raw data")
        val user = User(uuid).apply { initialize(raw) }
        log("user initialized")
        app.users[uuid] = user
        return user
    }

    private fun getRawUser(uuid: UUID) =
        socket.writeAndAwaitResponse<GetUserPackage>(GetUserPackage(uuid.toString()))[app.waitResponseTime, TimeUnit.SECONDS].data
}
