package me.slavita.construction.action.command.menu.lootbbox

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.click
import me.slavita.construction.utils.getWorkerInfo
import me.slavita.construction.utils.user
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class BuyLootboxMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return choicer {
                title = "${AQUA}${BOLD}Покупка работников"
                description = "Выбери нужного строителя"
                info = getWorkerInfo()
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    WorkerRarity.values().forEach { rarity ->
                        this@storage.add(button {
                            item = rarity.getIcon()
                            title = rarity.title
                            description = rarity.description + "\n\n${rarity.price.toMoneyIcon()}"
                            hint = "Выбрать"
                            backgroundColor = rarity.color
                            click { _, _, _ ->
                                ChoiceLootboxAmount(player, rarity).keepHistory().tryExecute()
                            }
                        })
                    }
                }
            }
        }
    }
}
