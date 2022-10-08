package me.slavita.construction.utils.extensions

import me.func.mod.Anime
import me.slavita.construction.multichat.ChatType
import me.slavita.construction.multichat.MultiChats
import org.bukkit.entity.Player
import ru.cristalix.core.formatting.Formatting

object LoggerUtils {
    fun Player.error(text: String) {
        MultiChats.sendPlayerMessage(this, ChatType.SYSTEM, Formatting.error(text))
    }

    fun Player.warn(text: String) {
        MultiChats.sendPlayerMessage(this, ChatType.SYSTEM, Formatting.warn(text))
    }

    fun Player.fine(text: String) {
        MultiChats.sendPlayerMessage(this, ChatType.SYSTEM, Formatting.fine(text))
    }

    fun Player.fine(text: Int) {
        MultiChats.sendPlayerMessage(this, ChatType.SYSTEM, Formatting.fine(text.toString()))
    }

    fun Player.killboard(text: String) {
        Anime.killboardMessage(this, text)
    }
}