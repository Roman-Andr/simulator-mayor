package me.slavita.construction.city.showcase

import me.func.atlas.Atlas
import me.func.mod.reactive.ReactiveBanner
import me.func.mod.world.Banners
import me.slavita.construction.app
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.utils.toYaw
import org.bukkit.block.BlockFace

object Showcases : IRegistrable {
    val showcases = arrayListOf<ShowcaseProperties>()

    override fun register() {
        val materials = app.allBlocks
        Atlas.find("showcases").getMapList("showcases").forEach { values ->
            showcases.add(
                ShowcaseProperties(
                    values["id"] as Int,
                    values["title"] as String,
                    hashSetOf<ShowcaseProduct>().apply {
                        materials.forEach { material ->
                            add(ShowcaseProduct(material, 100))
                        }
                    }
                )
            )
        }
        val banner = Atlas.find("showcases")
        app.mainWorld.map.getLabels("showcase-banner").forEach { label ->
            val face = BlockFace.valueOf(label.tag.uppercase())
            Banners.new(
                ReactiveBanner.builder()
                    .weight(banner.getInt("banner.weight"))
                    .height(banner.getInt("banner.height"))
                    .content(banner.getString("banner.content"))
                    .carveSize(2.0)
                    .x(label.toCenterLocation().x - face.modX * 0.49)
                    .y(label.toCenterLocation().y + 1.0)
                    .z(label.toCenterLocation().z - face.modZ * 0.49)
                    .yaw(face.toYaw())
                    .apply {
                        banner.getList("banner.lines-size").forEachIndexed { index, value ->
                            this.resizeLine(index, value as Double)
                        }
                    }
                    .build()
            )
        }
    }
}
