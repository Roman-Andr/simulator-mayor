package me.slavita.construction.worker

import me.slavita.construction.region.WorkerStructure
import me.slavita.construction.ui.Formatter.toLevel
import me.slavita.construction.ui.HumanizableValues.BLOCK
import org.bukkit.ChatColor.WHITE
import org.bukkit.ChatColor.GOLD
import java.util.UUID

class Worker(
    val name: String,
    val rarity: WorkerRarity,
    var level: Int,
    var blocksSpeed: Int,
    val reliability: Int,
    val rapacity: WorkerRapacity,
) {
    val uuid: UUID = UUID.randomUUID()
    var parent: WorkerStructure? = null

    val sellPrice: Long
        get() {
            return 199 //todo: Формула
        }

    val upgradePrice: Long
        get() {
            return 199 //todo: Формула
        }

    fun levelUp() {
        level += 1
        blocksSpeed *= 2
    }

    override fun toString() = """
        Имя: ${GOLD}$name
        Редкость: ${GOLD}${rarity.title}
        Уровень: ${level.toLevel()}
        Скорость: ${GOLD}${BLOCK.get(blocksSpeed)} ${WHITE}в секунду
        Надёжность: ${GOLD}$reliability%
        Жадность: ${GOLD}${rapacity.title}
    """.trimIndent()
}
