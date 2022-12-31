package me.slavita.construction.multichat

import me.func.mod.ui.MultiChat
import me.func.mod.util.after
import me.func.mod.util.safe
import me.func.protocol.data.chat.ModChat
import org.bukkit.entity.Player
import java.util.*


object MultiChats {
    private val chatList = arrayListOf<ModChat>()

    init {
        ChatType.values().forEach { chat ->
            ModChat(UUID.randomUUID(), chat.title, chat.symbol).apply {
                MultiChat.createKey(chat.key, this)
                chatList.add(this)
            }
        }
    }

    fun sendPlayerChats(player: Player) = safe { MultiChat.sendChats(player, *chatList.toTypedArray()) }

    fun removePlayerChats(player: Player) = MultiChat.removeChats(player, *chatList.toTypedArray())

    fun sendPlayerMessage(player: Player, type: ChatType, vararg messages: String) =
        messages.forEach { MultiChat.sendMessage(player, type.key, it) }
}