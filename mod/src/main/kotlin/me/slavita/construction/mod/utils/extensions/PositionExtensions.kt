package me.slavita.construction.mod.utils.extensions

import dev.xdark.clientapi.math.BlockPos
import ru.cristalix.uiengine.utility.V3

object PositionExtensions {
    fun BlockPos.equalsV(position: V3): Boolean {
        return x == position.x.toInt() && y == position.y.toInt() && z == position.z.toInt()
    }
}