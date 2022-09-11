package me.slavita.construction.multichat

import me.func.mod.MultiChat
import me.func.protocol.ModChat
import org.bukkit.entity.Player


object MultiChatUtil {
    private val chatList = mutableListOf<ModChat>()

    fun createChats() =
        ChatType.values().forEach {
            ModChat(it.uuid, it.title, it.symbol).apply {
                MultiChat.createKey(it.key, this)
                chatList.add(this)
            }
        }

    fun sendPlayerChats(player : Player) = MultiChat.sendChats(player, *chatList.toTypedArray());

    fun removePlayerChats(player : Player) = MultiChat.removeChats(player, *chatList.toTypedArray())

    fun sendPlayerMessage(player: Player, type: ChatType, vararg messages: String) = messages.forEach { MultiChat.sendMessage(player, type.key, it) }
}