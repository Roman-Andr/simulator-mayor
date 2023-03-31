package me.slavita.construction.region

import me.func.mod.world.Banners
import me.slavita.construction.utils.BannerInfo
import me.slavita.construction.utils.createBanner
import me.slavita.construction.utils.show
import org.bukkit.Location
import org.bukkit.entity.Player

class DualBanner(val player: Player, val info: BannerInfo) {

    val banners = info.run {
        listOf(
            createBanner(BannerInfo(source, blockFace, content, width, height, color, opacity, motionType, pitch)),
            createBanner(
                BannerInfo(
                    Location(
                        source.world,
                        source.x + blockFace.modX,
                        source.y,
                        source.z + blockFace.modZ
                    ),
                    blockFace.oppositeFace, content, width, height, color, opacity, motionType, pitch
                )
            )
        )
    }


    fun show() {
        Banners.show(player, *banners.toTypedArray())
    }

    fun setContent(content: String) {
        banners.forEach {
            Banners.content(player, it, content)
        }
    }
}
