package me.slavita.construction.mod.utils.extensions

import dev.xdark.clientapi.math.BlockPos
import ru.cristalix.uiengine.utility.V3

object PositionExtensions {
    fun BlockPos.equalsV(position: V3): Boolean {
        return x == position.x.toInt() && y == position.y.toInt() && z == position.z.toInt()
    }

    fun BlockPos.inBox(min: V3, max: V3): Boolean {
        return x >= min.x && y >= min.y && z >= min.z && x <= max.x && y <= max.y && z <= max.z
    }
}