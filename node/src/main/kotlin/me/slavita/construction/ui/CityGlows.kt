package me.slavita.construction.ui

import me.func.atlas.Atlas
import me.func.mod.reactive.ReactivePlace
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import me.slavita.construction.action.command.menu.city.CityHallMenu
import me.slavita.construction.action.command.menu.storage.StorageUpgrade
import me.slavita.construction.app
import me.slavita.construction.utils.loadBanner
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.label
import org.bukkit.entity.Player

object CityGlows {
    init {
        loadGlow("city-hall", GlowColor.GREEN, { player ->
            CityHallMenu(player).tryExecute()
        })
        loadGlow("afk-zone", GlowColor.RED_LIGHT, { player ->
            player.accept("Вы вошли в зону афк")
        }, { player ->
            player.accept("Вы вышли из зоны афк")
        })
        loadGlow("storage-upgrade", GlowColor.GREEN_LIGHT, { player ->
            StorageUpgrade(player).tryExecute()
        })
    }

    private fun loadGlow(
        labelName: String,
        color: RGB,
        onEnter: (player: Player) -> Unit,
        onLeave: (player: Player) -> Unit = {},
        radius: Double = 2.0,
    ) {
        label(labelName)?.let { label ->
            Atlas.find("city").getMapList(labelName).forEach { banner ->
                loadBanner(banner, label, true, 0.0)
            }
            app.mainWorld.glows.add(
                ReactivePlace.builder()
                    .rgb(color)
                    .radius(radius)
                    .location(label.toCenterLocation().clone().apply { y -= 2.5 })
                    .onEntire(onEnter)
                    .onLeave(onLeave)
                    .build().apply {
                        isConstant = true
                    }
            )
        }
    }
}