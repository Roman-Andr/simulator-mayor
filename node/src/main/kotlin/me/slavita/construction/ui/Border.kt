package me.slavita.construction.ui

import me.func.mod.conversation.ModTransfer
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import me.slavita.construction.common.utils.CREATE_BORDER_CHANNEL
import me.slavita.construction.common.utils.DELETE_BORDER_CHANNEL
import me.slavita.construction.utils.uuidF
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.UUID

class Border(
    var color: RGB = GlowColor.NEUTRAL,
    var alpha: Int = 100,
    var width: Double = 25.0,
    var height: Double = 50.0,
    var location: Vector = Vector(0.0, 0.0, 0.0),
) {
    var uuid: UUID = UUID.randomUUID()

    fun send(vararg player: Player) {
        ModTransfer()
            .uuidF(uuid)
            .rgb(color)
            .integer(alpha)
            .double(width)
            .double(height)
            .v3(location)
            .send(CREATE_BORDER_CHANNEL, *player)
    }

    fun delete(vararg player: Player) {
        ModTransfer()
            .uuidF(uuid)
            .send(DELETE_BORDER_CHANNEL, *player)
    }

    fun update(vararg player: Player) {
        ModTransfer()
            .uuidF(uuid)
            .rgb(color)
            .send(DELETE_BORDER_CHANNEL, *player)
    }

    companion object {
        fun builder() = Builder()
    }

    class Builder(val model: Border = Border()) {
        fun color(color: RGB) = apply { model.color = color }
        fun width(width: Double) = apply { model.width = width }
        fun alpha(alpha: Int) = apply { model.alpha = alpha }
        fun height(height: Double) = apply { model.height = height }
        fun location(location: Location) = apply { model.location = location.toVector() }
        fun build() = model
    }
}
