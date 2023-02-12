package me.slavita.construction.city

import me.func.mod.reactive.ReactivePlace
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import me.slavita.construction.action.menu.city.CityHallMenu
import me.slavita.construction.action.menu.city.StorageUpgrade
import me.slavita.construction.app
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.ui.BannerSamples
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.labels
import me.slavita.construction.utils.newBanner
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

object CityGlows : IRegistrable {
    override fun register() {
        loadGlow(BannerSamples.CITY_HALL, GlowColor.GREEN, { player ->
            CityHallMenu(player).tryExecute()
        })
        loadGlow(BannerSamples.AFK_ZONE, GlowColor.RED_LIGHT, { player ->
            player.accept("Вы вошли в зону афк")
        }, { player ->
            player.accept("Вы вышли из зоны афк")
        })
        loadGlow(BannerSamples.STORAGE_UPGRADE, GlowColor.GREEN_LIGHT, { player ->
            StorageUpgrade(player).tryExecute()
        })
        loadGlow(BannerSamples.TRASH, GlowColor.NEUTRAL_LIGHT, { player ->
            player.accept(
                """
                Здесь находится мусорка
                Выкиньте блоки, чтобы удалить их
                """.trimIndent()
            )
            player.user.inTrashZone = true
        }, { player ->
            player.user.inTrashZone = false
        })
    }

    private fun loadGlow(
        banner: BannerSamples,
        color: RGB,
        onEnter: (player: Player) -> Unit,
        onLeave: (player: Player) -> Unit = {},
        radius: Double = 2.0,
    ) {
        labels(banner.label).forEach { label ->
            newBanner(banner, label, true, 0.0)
            app.mainWorld.glows.add(
                ReactivePlace.builder()
                    .rgb(color)
                    .radius(radius)
                    .location(label.toCenterLocation().clone().apply { y -= 2.5 })
                    .onEntire { player ->
                        if (player.location.distance(label) > 3) return@onEntire
                        onEnter(player)
                    }
                    .onLeave { player ->
                        onLeave(player)
                    }
                    .build().apply {
                        isConstant = true
                    }
            )
        }
    }
}
