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
        blocksSpeed *= 2
    }

    override fun toString(): String {
        return """
            ${AQUA}Имя: $name
            ${AQUA}Редкость: ${rarity.title}
            ${AQUA}Уровень: ${level}${WHITE}${Emoji.UP}
            ${AQUA}Скорость: $blocksSpeed ${Humanize.plurals("блок", "блока", "блоков", blocksSpeed)} в секунду
            ${AQUA}Надёжность: $reliability
            ${AQUA}Жадность: ${rapacity.title}
        """.trimIndent()
    }
}