package me.slavita.construction.player

import dev.implario.kensuke.KensukeSession
import dev.implario.kensuke.KensukeUser
import dev.implario.kensuke.impl.bukkit.IBukkitKensukeUser
import me.slavita.construction.app
import org.bukkit.entity.Player
import java.util.*

class KensukeUser(uuid: UUID, data: Data?, session: KensukeSession) : KensukeUser(session), IBukkitKensukeUser {
    val user = app.getUserOrAdd(uuid)

    init {
        if (!user.initialized) user.data = data ?: Data()
    }

    override fun setPlayer(player: Player?) {
        if (player != null) {
            user.player = player
        }
    }

    override fun getPlayer() = if (user.initialized) user.player else null
}
