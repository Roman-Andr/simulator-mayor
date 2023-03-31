package me.slavita.construction.worker

import me.func.protocol.data.color.GlowColor

enum class WorkerState(val title: String, val color: GlowColor) {

    FREE("Выбрать", GlowColor.BLUE),
    SELECTED("Выбран", GlowColor.ORANGE),
    BUSY("Занят", GlowColor.NEUTRAL);
}
