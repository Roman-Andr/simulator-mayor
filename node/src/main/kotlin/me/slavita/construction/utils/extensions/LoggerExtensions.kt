package me.slavita.construction.utils.extensions

import me.slavita.construction.utils.multichat.ChatType
import me.slavita.construction.utils.multichat.MultiChatUtil
import org.bukkit.entity.Player
import ru.cristalix.core.formatting.Formatting

object LoggerUtils {
    fun Player.error(text: String) {
        MultiChatUtil.sendPlayerMessage(this, ChatType.SYSTEM, Formatting.error(text))
    }

    fun Player.warn(text: String) {
        MultiChatUtil.sendPlayerMessage(this, ChatType.SYSTEM, Formatting.warn(text))
    }

    fun Player.fine(text: String) {
        MultiChatUtil.sendPlayerMessage(this, ChatType.SYSTEM, Formatting.fine(text))
    }

    fun Player.fine(text: Int) {
        MultiChatUtil.sendPlayerMessage(this, ChatType.SYSTEM, Formatting.fine(text.toString()))
    }
}