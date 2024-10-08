package me.slavita.construction.city

import me.func.mod.reactive.ReactiveBanner
import me.func.mod.reactive.ReactivePlace
import me.func.mod.ui.Glow
import me.func.mod.util.after
import me.func.mod.world.Banners
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.ui.BannerSamples
import me.slavita.construction.ui.Texture
import me.slavita.construction.utils.labels
import me.slavita.construction.utils.newBanner
import me.slavita.construction.utils.revert
import me.slavita.construction.utils.toYaw
import me.slavita.construction.utils.yaw
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

object SpeedPlaces : IRegistrable {

    private val active = hashSetOf<Player>()

    override fun register() {
        labels("speed").forEach { label ->
            val yaw = BlockFace.valueOf(label.tag.uppercase()).toYaw().revert()
            val loc = label.yaw(yaw).toCenterLocation().apply { y = label.y }
            val location = loc.clone()
                .add(loc.direction.normalize().multiply(-1.25))
                .toCenterLocation()
                .apply { y = label.y }

            newBanner(BannerSamples.SPEED_PLACE, label, true, 0.0)
            Banners.add(
                ReactiveBanner.builder()
                    .texture(Texture.SPEED_BOOST.path())
                    .color(GlowColor.BLUE_LIGHT)
                    .weight(35)
                    .height(35)
                    .opacity(1.0)
                    .x(location.x)
                    .y(location.y + 0.01)
                    .z(location.z)
                    .yaw(yaw)
                    .pitch(-90F)
                    .build()
            )
            val glow = ReactivePlace.builder()
                .rgb(GlowColor.BLUE_MIDDLE)
                .radius(1.5)
                .location(loc.clone().apply { y -= 3.5 })
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
            app.mainWorld.glows.add(glow)
        }
    }
}
