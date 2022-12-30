package me.slavita.construction.player

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import dev.implario.kensuke.KensukeSession
import dev.implario.kensuke.KensukeUser
import dev.implario.kensuke.impl.bukkit.IBukkitKensukeUser
import me.slavita.construction.app
import me.slavita.construction.utils.log
import org.bukkit.entity.Player
import java.util.*

class KensukeUser(uuid: UUID, json: JsonElement, session: KensukeSession) : KensukeUser(session), IBukkitKensukeUser {
    val user = app.getUserOrAdd(uuid)

    init {
        try {
            val gson = GsonBuilder()
                .registerTypeAdapter(City::class.java, CityDeserializer(user))
                .setPrettyPrinting()
                .create()

            user.data = gson.fromJson(json, Data::class.java)
        } catch (e: Exception) {
            log("Cannot found data on kensuke! Creating new empty...")
            if (!user.initialized) user.data = Data(user)
        }
    }

    override fun setPlayer(player: Player?) {
        if (player != null) {
            user.player = player
        }
    }

    override fun getPlayer() = if (user.initialized) user.player else null
}
