package me.slavita.construction.structure.tools

import me.func.mod.reactive.ReactiveProgress
import me.func.protocol.data.color.Tricolor
import me.func.protocol.math.Position
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class StructureProgressBar(val player: Player, private val blocksTotal: Int) {
    private val bar = ReactiveProgress.builder()
        .position(Position.BOTTOM)
        .offsetY(31.0)
        .hideOnTab(false)
        .color(Tricolor(36, 175, 255))
        .build()

    var hidden = false
    var blocksPlaced = 0

    fun show() {
        if (!hidden) return
        hidden = false

        bar.apply {
            update(blocksPlaced)
            send(player)
            progress = 0.0
        }
    }

    fun update(blocksPlaced: Int) {
        this.blocksPlaced = blocksPlaced
        bar.apply {
            progress = blocksPlaced.toDouble() / blocksTotal.toDouble()
            text = "${WHITE}Поставлено блоков: ${WHITE}$blocksPlaced ${WHITE}из ${AQUA}$blocksTotal"
        }
    }

    fun hide() {
        if (hidden) return
        hidden = true

        bar.delete(setOf(player))
    }
}
