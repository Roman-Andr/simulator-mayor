package me.slavita.construction.worker

import implario.humanize.Humanize
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.ui.Formatter.toLevel
import org.bukkit.ChatColor.*
import java.util.*

class Worker(
    val name: String,
    val rarity: WorkerRarity,
    var level: Int,
    var blocksSpeed: Int,
    val reliability: Int,
    val rapacity: WorkerRapacity,
) {
    val uuid = UUID.randomUUID()

    val sellPrice: Long
        get() {
            return 199 /* Формула */
        }

    val upgradePrice: Long
        get() {
            return 199 /* Формула */
        }

    fun levelUp() {
        level += 1
        blocksSpeed *= 2
    }

    override fun toString(): String {
        return """
            ${AQUA}Имя: ${GOLD}$name
            ${AQUA}Редкость: ${GOLD}${rarity.title}
            ${AQUA}Уровень: ${level.toLevel()}
            ${AQUA}Скорость: ${GOLD}$blocksSpeed ${Humanize.plurals("блок", "блока", "блоков", blocksSpeed)} в секунду
            ${AQUA}Надёжность: ${GOLD}$reliability%
            ${AQUA}Жадность: ${GOLD}${rapacity.title}
        """.trimIndent()
    }
}