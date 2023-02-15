package me.slavita.construction.action.menu.city

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.Texture
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.LOCATIONS_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.mapM
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.entity.Player

class LocationsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${GOLD}${BOLD}Телепортация"
                description = "Перемещение между локациями"
                info = LOCATIONS_INFO
                storage = user.data.cities.sortedBy { it.price }.mapM { city ->
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
                                tryChangeCity(city)
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
