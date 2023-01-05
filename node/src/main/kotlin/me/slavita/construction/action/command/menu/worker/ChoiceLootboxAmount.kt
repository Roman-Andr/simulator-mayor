package me.slavita.construction.action.command.menu.worker

import me.func.mod.Anime
import me.func.mod.conversation.data.LootDrop
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.choicer
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.HumanizableValues
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.*
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
                info = getWorkerInfo()
                storage = listOf(
                    Pair(1, "common_key"),
                    Pair(5, "rare_key"),
                    Pair(10, "mific_key")
                ).mapM {
                    button {
                        item = ItemIcons.get("other", it.second)
                        title = "${it.first} ${HumanizableValues.LOOTBOX.get(it.first)}"
                        description = (rarity.price * it.first).toMoneyIcon()
                        hint = "Купить"
                        onClick { _, _, _ ->
                            if (!player.user.canPurchase(rarity.price * it.first)) {
                                Anime.close(player)
                                player.deny("Недостаточно средств!")
                                return@onClick
                            }
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