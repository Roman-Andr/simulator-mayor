package me.slavita.construction.utils

import dev.implario.bukkit.platform.Platforms
import me.func.world.Label
import me.func.world.WorldMeta
import me.slavita.construction.app
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.Event
import org.bukkit.event.EventPriority

fun labels(key: String, map: WorldMeta = app.mainWorld.map): List<Label> {
    return map.getLabels(key)
}

fun Float.revert() = when {
    this >= 0 -> this - 180F
    else -> this + 180F
}

fun Location.yaw(yaw: Float) = apply { setYaw(yaw) }