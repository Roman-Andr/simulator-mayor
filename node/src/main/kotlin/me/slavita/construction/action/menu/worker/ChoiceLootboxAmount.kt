package me.slavita.construction.action.menu.worker

import me.func.mod.conversation.data.LootDrop
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.HumanizableValues.LOOTBOX
import me.slavita.construction.ui.menu.Icons
import me.slavita.construction.utils.WORKER_INFO
import me.slavita.construction.utils.click
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.user
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class ChoiceLootboxAmount(player: Player, val rarity: WorkerRarity) : MenuCommand(player) {
    override fun getMenu(): Openable {
        user.run user@{
            return choicer {
                title = "${AQUA}${BOLD}Купить лутбоксы"
                description = "Выберите необходимое количество лутбоксов"
                info = WORKER_INFO
                storage = listOf(
                    Pair(1, "common_key"),
                    Pair(5, "rare_key"),
                    Pair(10, "mific_key")
                ).mapM {
                    button {
                        item = Icons.get("other", it.second)
                        title = LOOTBOX.get(it.first)
                        description = (rarity.price * it.first).toMoneyIcon()
                        hint = "Купить"
                        click { _, _, _ ->
                            player.user.tryPurchase(rarity.price * it.first) {
                                val workers = WorkerGenerator.generate(rarity, it.first)
                                val lootDrop = mutableListOf<LootDrop>()
                                workers.forEach {
                                    lootDrop.add(LootDrop(rarity.getIcon(), it.name, rarity.dropRare))
                                }
                                OpenWorker(this@user, *workers.toTypedArray()).tryExecute()
                            }
                        }
                    }
                }
            }
        }
    }
}
