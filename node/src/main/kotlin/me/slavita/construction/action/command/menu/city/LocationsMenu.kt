package me.slavita.construction.action.command.menu.city

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.ChangeCity
import me.slavita.construction.dontate.AbilityDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.HumanizableValues.SECOND
import me.slavita.construction.ui.Texture
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.*
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player
import kotlin.math.abs

class LocationsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${AQUA}${BOLD}Телепортация"
                description = "Перемещение между локациями"
                info = LOCATIONS_INFO
                storage = this@uses.data.cities.sortedBy { it.price }.mapM { city ->
                    button {
                        title = city.title
                        if (city.unlocked) {
                            texture = Texture.LOCATION.path()
                            hint = "Выбрать"
                            backgroundColor = GlowColor.GREEN
                        } else {
                            item = Icons.get("other", "lock")
                            hint = "Купить"
                            description = city.price.toMoneyIcon()
                            backgroundColor = GlowColor.NEUTRAL
                        }
                        click { _, _, _ ->
                            if (city.unlocked) {
                                val ignore =
                                    player.user.data.abilities.contains((Donates.NO_LIMIT_TELEPORT_DONATE.donate as AbilityDonate).ability)
                                ChangeCity(
                                    user,
                                    city
                                ).tryExecute(ignore)
                                    .run {
                                        if (!ignore && this < 0) player.deny(
                                            "Подождите ещё ${SECOND.get((abs(this) / 20).toInt())}"
                                        )
                                    }
                                Anime.close(player)
                            } else {
                                BuyCityConfirm(player, city).tryExecute()
                            }

                        }
                    }
                }
            }
        }
    }
}
