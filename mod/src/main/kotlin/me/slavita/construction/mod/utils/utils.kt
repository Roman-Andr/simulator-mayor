package me.slavita.construction.mod.utils

import dev.xdark.clientapi.ClientApi
import dev.xdark.clientapi.world.World
import me.slavita.construction.mod.SpecialColor
import me.slavita.construction.mod.utils.extensions.PositionExtensions.string
import me.slavita.construction.mod.utils.extensions.PositionExtensions.withOffset
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.utility.Color
import ru.cristalix.uiengine.utility.V3
import kotlin.math.*

fun getWidth(string: String): Double = clientApi.fontRenderer().getStringWidth(string).toDouble()

fun Double.doubleVec() = V3(this, this, this)

fun ClientApi.isLookingAt(location: V3): Boolean {
    val player = minecraft().renderViewEntity
    val eyeLocation = V3(player.x, player.y + player.eyeHeight, player.z)

    val yaw = (player.rotationYaw + 90) * (Math.PI / 180)
    val pitch = (player.rotationPitch + 90) * (Math.PI / 180)

    var blocksPassed = 0.0
    val xx = sin(pitch) * cos(yaw)
    val yy = cos(pitch)
    val zz = sin(yaw) * sin(pitch)

    while (blocksPassed <= 3.5) {
        val currentLocation = V3(
            blocksPassed * xx + eyeLocation.x,
            blocksPassed * yy + eyeLocation.y,
            blocksPassed * zz + eyeLocation.z
        )
        //Renderer.drawLine(eyeLocation, currentLocation, SpecialColor.RED.toColor(), 5f)

        if (currentLocation.distanceSquared3(location.withOffset(0.5, 0.5, 0.5)) <= 0.9) return true

        blocksPassed += 0.1
        //if (!minecraft().world.isAirBlock(round(location.x).toInt(), round(location.y).toInt(), round(location.z).toInt())) return false
    }

    return false
}
