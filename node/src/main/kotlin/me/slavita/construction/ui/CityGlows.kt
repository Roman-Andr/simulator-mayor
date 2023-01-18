package me.slavita.construction.ui

import me.func.atlas.Atlas
import me.func.mod.reactive.ReactivePlace
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.color.RGB
import me.slavita.construction.action.command.menu.city.CityHallMenu
import me.slavita.construction.action.command.menu.storage.StorageUpgrade
import me.slavita.construction.app
import me.slavita.construction.utils.*
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
        loadGlow("trash", GlowColor.NEUTRAL_LIGHT, { player ->
            player.accept("""
                Здесь находится мусорка
                Выкиньте блоки, чтобы удалить их
            """.trimIndent())
            player.user.inTrashZone = true
        }, { player ->
            player.user.inTrashZone = false
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
                    .onEntire { player ->
                        if (player.location.distance(label) > 3) return@onEntire
                        onEnter(player)
                    }
                    .onLeave { player ->
                        if (player.location.distance(label) > 3) return@onLeave
                        onLeave(player)
                    }
                    .build().apply {
                        isConstant = true
                    }
            )
        }
    }
}