package me.slavita.construction.action.command.menu

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer.Choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.ChangeCity
import me.slavita.construction.dontate.AbilityDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.ui.HumanizableValues.SECOND
import me.slavita.construction.utils.Texture
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.user
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player
import ru.cristalix.core.formatting.Formatting.error
import kotlin.math.abs

class LocationsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return Choicer(title = "${AQUA}${BOLD}Телепортация", description = "Перемещение между локациями").apply {
                storage = this@user.cities.map { city ->
                    button {
                        texture = Texture.LOCATION.path()
                        title = city.title
                        hint = "Выбрать"
                        backgroundColor = GlowColor.GREEN
                        onClick { _, _, _ ->
                            ChangeCity(
                                player,
                                city
                            ).tryExecute(player.user.abilities.contains((Donates.NO_LIMIT_TELEPORT_DONATE.donate as AbilityDonate).ability))
                                .run {
                                    if (this < 0) player.killboard(
                                        error(
                                            "Подождите ещё ${(abs(this) / 20).toInt()} ${SECOND.get((abs(this) / 20).toInt())}"
                                        )
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