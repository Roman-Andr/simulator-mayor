package me.slavita.construction.worker

import implario.humanize.Humanize
import me.func.protocol.data.emoji.Emoji
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

class Worker(
    val name: String,
    val rarity: WorkerRarity,
    var level: Int,
    var blocksSpeed: Int,
    val reliability: Int,
    val rapacity: WorkerRapacity
) {
    val uuid = UUID.randomUUID()

    val sellPrice: Long
        get() { return 199 /* Формула */ }

    val upgradePrice: Long
        get() { return 199 /* Формула */ }

    fun levelUp() {
        level += 1
        blocksSpeed = (blocksSpeed * 2).toInt()
    }

    override fun toString(): String {
        return Stream.of(
            "${AQUA}Имя: ${name}\n",
            "${AQUA}Редкость: ${rarity.title}\n",
            "${AQUA}Уровень: ${level}${WHITE}${Emoji.UP}\n",
            "${AQUA}Скорость: $blocksSpeed ${Humanize.plurals("блок", "блока", "блоков", blocksSpeed)} в секунду\n",
            "${AQUA}Надёжность: ${reliability}\n",
            "${AQUA}Жадность: ${rapacity.title}\n"
        ).collect(Collectors.joining())
    }
}