package me.slavita.construction.action.menu.project

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.region.RegionOptions
import me.slavita.construction.region.Regions
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.*
import me.slavita.construction.utils.language.LanguageHelper
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class StructureRegionChoicer(
    player: Player,
    val onSubmit: (options: RegionOptions) -> Unit
) : MenuCommand(player, 3 * 20) {

    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${ChatColor.GREEN}${ChatColor.BOLD}Выбор здания"
                info = STRUCTURES_INFO
                size(5, 4)
                storage = Regions.regions.values.mapM { region ->
                    button {
                        title = region.name
                        hint = "Выбрать"
                        item = Icons.get("skyblock", "spawn")
                        hover = ""
                        click { _, _, _ ->
                            Anime.close(player)
                            onSubmit(region)
                        }
                    }
                }
            }
        }
    }
}
