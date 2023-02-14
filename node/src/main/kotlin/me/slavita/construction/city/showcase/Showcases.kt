package me.slavita.construction.city.showcase


import me.func.mod.world.Banners
import me.slavita.construction.app
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.ui.BannerSamples
import me.slavita.construction.utils.loadBanner
import me.slavita.construction.utils.toYaw
import org.bukkit.block.BlockFace

object Showcases : IRegistrable {
    val showcases = arrayListOf(
        ShowcaseProperties(1, "§a§lМагазин \"Напильник\"", hashSetOf()),
        ShowcaseProperties(2, "§a§lМагазин \"Мастер\"", hashSetOf()),
        ShowcaseProperties(3, "§a§lМагазин \"Стружка\"", hashSetOf()),
        ShowcaseProperties(4, "§a§lМагазин \"Кран\"", hashSetOf()),
        ShowcaseProperties(5, "§a§lМагазин \"Пила\"", hashSetOf()),
        ShowcaseProperties(6, "§a§lМагазин \"Грузовой\"", hashSetOf()),
    )

    override fun register() {
        val materials = app.allBlocks
        showcases.forEach {
            it.elements = hashSetOf<ShowcaseProduct>().apply {
                materials.forEach { material ->
                    add(ShowcaseProduct(material, 100))
                }
            }
        }
        app.mainWorld.map.getLabels("showcase-banner").forEach { label ->
            val face = BlockFace.valueOf(label.tag.uppercase())
            Banners.new(
                loadBanner(BannerSamples.SHOWCASE, label, withOutWatch = true, yaw = face.toYaw()).apply {
                    x = label.toCenterLocation().x - face.modX * 0.49
                    y = label.toCenterLocation().y + 1.0
                    z = label.toCenterLocation().z - face.modZ * 0.49
                }
            )
        }
    }
}
