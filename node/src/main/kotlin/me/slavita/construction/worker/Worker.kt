package me.slavita.construction.worker

import me.func.protocol.data.emoji.Emoji
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class Worker(
    val name: String,
    val rarity: WorkerRarity,
    val skill: Int,
    val reliability: Int,
    val rapacity: WorkerRapacity
) {
    val uuid = UUID.randomUUID()

    val sellPrice: Long
        get() { return 199 /* Формула */ }

    override fun toString(): String {
        return Stream.of(
            "${AQUA}Имя: ${name}\n",
            "${AQUA}Редкость: ${rarity.title}\n",
            "${AQUA}Уровень: ${skill}${WHITE}${Emoji.UP}\n",
            "${AQUA}Надёжность: ${reliability}\n",
            "${AQUA}Жадность: ${rapacity.title}\n"
        ).collect(Collectors.joining())
    }
}