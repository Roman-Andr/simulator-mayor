package me.slavita.construction.structure.client

import me.func.mod.reactive.ReactiveProgress
import me.func.protocol.data.color.Tricolor
import me.func.protocol.math.Position
import org.bukkit.entity.Player

class StructureProgressBar(val player: Player, private val blocksTotal: Int) {
    private val bar = ReactiveProgress.builder()
        .position(Position.BOTTOM)
        .offsetY(24.0)
        .hideOnTab(true)
        .color(Tricolor(36, 175, 255))
        .text("§aПоставлено блоков: §b0/0")
        .build()

    fun show() {
        bar.apply {
            send(player)
            progress = 0.0
        }
    }

    fun update(blocksPlaced: Int) {
        bar.apply {
            progress = blocksPlaced.toDouble() / blocksTotal.toDouble()
            text = "§aПоставлено блоков: §b$blocksPlaced/$blocksTotal"
        }
    }

    fun hide() {
        bar.delete(setOf(player))
    }
}
