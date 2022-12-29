package me.slavita.construction.worker

import me.slavita.construction.ui.Formatter.toLevel
import me.slavita.construction.ui.HumanizableValues
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GOLD
import java.util.*

class Worker(
    val name: String,
    val rarity: WorkerRarity,
    var level: Int,
    var blocksSpeed: Int,
    val reliability: Int,
    val rapacity: WorkerRapacity,
) {
    val uuid: UUID = UUID.randomUUID()

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

    override fun toString() = """
        ${AQUA}Имя: ${GOLD}$name
        ${AQUA}Редкость: ${GOLD}${rarity.title}
        ${AQUA}Уровень: ${level.toLevel()}
        ${AQUA}Скорость: ${GOLD}$blocksSpeed ${HumanizableValues.BLOCK.get(blocksSpeed)} в секунду
        ${AQUA}Надёжность: ${GOLD}$reliability%
        ${AQUA}Жадность: ${GOLD}${rapacity.title}
    """.trimIndent()
}