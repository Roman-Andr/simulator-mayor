package me.slavita.construction.ui

import me.func.atlas.Atlas
import me.func.mod.reactive.ReactivePlace
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.menu.CityHallMenu
import me.slavita.construction.app
import me.slavita.construction.banner.BannerUtil.loadBanner
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.label
import ru.cristalix.core.formatting.Formatting.fine

object CityGlows {
    init {
        label("city-hall")?.let { label ->
            Atlas.find("city").getMapList("city-hall").forEach { banner ->
                loadBanner(banner, label, true, 0.0)
            }
            app.mainWorld.glows.add(
                ReactivePlace.builder()
                    .rgb(GlowColor.GREEN)
                    .radius(2.0)
                    .x(label.toCenterLocation().x)
                    .y(label.y - 2.50)
                    .z(label.toCenterLocation().z)
                    .onEntire { player ->
                        CityHallMenu(player).tryExecute()
                    }
                    .build().apply {
                        isConstant = true
                    }
            )
        }
        label("afk-zone")?.let { label ->
            Atlas.find("city").getMapList("afk-zone").forEach { banner ->
                loadBanner(banner, label, true, 0.0)
            }
            app.mainWorld.glows.add(
                ReactivePlace.builder()
                    .rgb(GlowColor.RED_LIGHT)
                    .radius(3.0)
                    .x(label.toCenterLocation().x)
                    .y(label.y - 2.50)
                    .z(label.toCenterLocation().z)
                    .onEntire { player ->
                        player.killboard(fine("Вы вошли в зону афк"))
                    }
                    .onLeave { player ->
                        player.killboard(fine("Вы вышли из зоны афк"))
                    }
                    .build().apply {
                        isConstant = true
                    }
            )
        }
    }
}