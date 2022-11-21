package me.slavita.construction.ui

import me.func.mod.reactive.ReactivePlace
import me.func.mod.ui.Glow
import me.func.mod.util.after
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.element.Banner
import me.slavita.construction.utils.extensions.BlocksExtensions.toYaw
import me.slavita.construction.utils.labels
import me.slavita.construction.utils.revert
import me.slavita.construction.utils.yaw
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

object SpeedPlaces {

    private val active = hashSetOf<Player>()
    val glows = hashSetOf<ReactivePlace>()

    init {
        labels("speed").forEach { place ->
            val texture = "cache/animation:arrows-no-color.png"
            val yaw = BlockFace.valueOf(place.tag.uppercase()).toYaw().revert()
            val loc = place.yaw(yaw)
            val vector = loc.direction.normalize()
            val location = loc.clone().add(vector.multiply(-1.25))

            val banner = Banner.builder()
                .texture(texture)
                .red(0)
                .green(191)
                .blue(255)
                .weight(35)
                .height(35)
                .opacity(1.0)
                .x(location.x)
                .y(location.y + 0.01)
                .z(location.z)
                .yaw(yaw)
                .pitch(-90F)
                .build()
            val glow = ReactivePlace.builder()
                .rgb(GlowColor.BLUE_MIDDLE)
                .radius(1.5)
                .x(loc.x)
                .y(loc.y - 3.50)
                .z(loc.z)
                .onEntire { player ->
                    if (active.contains(player)) return@onEntire

                    Glow.animate(player, 3.0, GlowColor.BLUE_MIDDLE, 3.0)
                    player.walkSpeed += 0.3F
                    active.add(player)

                    after(5 * 20) {
                        player.walkSpeed -= 0.3F
                        active.remove(player)
                    }
                }
                .build().apply {
                    isConstant = true
                }

            Banners.add(banner)
            glows.add(glow)

        }
    }
}