package me.slavita.construction.action.menu.project

import me.func.mod.Anime
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.region.RegionOptions
import me.slavita.construction.region.Regions
import me.slavita.construction.region.StructureOptions
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.STRUCTURES_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.size
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.entity.Player

class StructureChoicer(
    player: Player,
    val regionOptions: RegionOptions,
    val onSubmit: (options: StructureOptions) -> Unit
) : MenuCommand(player, 3 * 20) {

    override fun getMenu(): Openable {
        user.run user@{
            return selection {
                title = "${GREEN}${BOLD}Выбор здания"
                info = STRUCTURES_INFO
                size(5, 4)
                storage = Regions.structures.values.filter { it.region == regionOptions }.mapM { structure ->
                    button {
                        title = structure.name
                        hint = "Выбрать"
                        item = Icons.get("skyblock", "spawn")
                        hover = ""
                        structure.blocks.toList().sortedBy { it.second }.toMap().forEach { block ->
                            hover += "${GOLD}${
                            LanguageHelper.getItemDisplayName(
                                block.key.createItemStack(1),
                                player
                            )
                            } - ${GREEN}${block.value}шт\n"
                        }
                        click { _, _, _ ->
                            Anime.close(player)
                            onSubmit(structure)
                        }
                    }
                }
            }
        }
    }
}
