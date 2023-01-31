package me.slavita.construction.mod.utils

import dev.xdark.clientapi.ClientApi
import dev.xdark.clientapi.inventory.InventoryPlayer
import dev.xdark.clientapi.item.ItemStack
import dev.xdark.clientapi.math.BlockPos
import dev.xdark.clientapi.render.BufferBuilder
import dev.xdark.clientapi.render.Tessellator
import io.netty.buffer.ByteBuf
import me.func.protocol.data.color.GlowColor
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.utility.Color
import ru.cristalix.uiengine.utility.V3
import kotlin.math.cos
import kotlin.math.sin

val minecraft = clientApi.minecraft()

val entity
    get() = minecraft.renderViewEntity

val ticks
    get() = minecraft.timer.renderPartialTicks

val prevX
    get() = entity.prevX

val prevY
    get() = entity.prevY

val prevZ
    get() = entity.prevZ

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

fun GlowColor.toColor(): Color {
    return Color(red, green, blue, 1.0)
}

fun InventoryPlayer.blocksCount(target: ItemStack): Int {
    var count = 0
    (0..35).forEach { slotId ->
        val item = getStackInSlot(slotId) ?: return@forEach
        if (item.item != null && item.item.id == target.item.id && item.metadata == target.metadata) count += item.count
    }
    return count
}

fun InventoryPlayer.hotbarEqualSlots(target: ItemStack): ArrayList<Int> {
    val list = arrayListOf<Int>()

    (0..8).forEach { slotId ->
        val item = getStackInSlot(slotId) ?: return@forEach
        if (item.item != null && item.item.id == target.item.id && item.metadata == target.metadata) list.add(slotId)
    }

    return list
}

fun InventoryPlayer.handItemEquals(targetItem: ItemStack): Boolean {
    return currentItem.item != null && currentItem.metadata == targetItem.metadata && currentItem.item.id == targetItem.item.id
}

fun BlockPos.equalsV(position: V3): Boolean {
    return x == position.x.toInt() && y == position.y.toInt() && z == position.z.toInt()
}

fun BlockPos.inBox(min: V3, max: V3): Boolean {
    return x >= min.x && y >= min.y && z >= min.z && x <= max.x && y <= max.y && z <= max.z
}

fun V3.withOffset(x: Double, y: Double, z: Double) = V3(this.x + x, this.y + y, this.z + z)

fun V3.string() = "V3(x: $x, y: $y, z: $z)"

val tessellator: Tessellator
    get() = clientApi.tessellator()

val bufferBuilder: BufferBuilder
    get() = tessellator.bufferBuilder

fun sendPayload(channel: String, buffer: ByteBuf) = clientApi.clientConnection().sendPayload(channel, buffer)
