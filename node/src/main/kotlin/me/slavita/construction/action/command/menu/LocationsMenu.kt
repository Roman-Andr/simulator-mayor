package me.slavita.construction.action.command.menu

import implario.humanize.Humanize
import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.ChangeCity
import me.slavita.construction.utils.Texture
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player
import kotlin.math.abs

class LocationsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Choicer(title = "Телепортация", description = "Перемещение между локациями").apply {
                storage = this@user.cities.map { city ->
                    button {
                        texture = Texture.LOCATION.path()
                        title = city.title
                        hint = "Выбрать"
                        backgroundColor = GlowColor.GREEN
                        onClick { _, _, _ ->
                            ChangeCity(player, city).tryExecute(false).run {
                                if (this < 0) player.killboard(
                                    "${GREEN}Подождите ещё ${abs(this) / 20} ${
                                        Humanize.plurals(
                                            "секунду", "секунды", "секунд",
                                            (abs(this) / 20).toInt()
                                        )
                                    }"
                                )
                            }
                            Anime.close(player)
                        }
                    }
                }.toMutableList()
            }
        }
    }
}