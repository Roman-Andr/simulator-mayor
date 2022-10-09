package me.slavita.construction.action.command.menu.worker

import me.func.mod.conversation.data.LootDrop
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.action.command.OpenWorker
import me.slavita.construction.app
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.entity.Player

class WorkerBuyMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return get(MenuInfo("Покупка строителей", StatsType.MONEY, 3, 3)).apply {
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    WorkerRarity.values().forEach { rarity ->
                        val canBuy = stats.money >= rarity.price
                        val iconItem = ItemIcons.get(rarity.iconKey, rarity.iconValue, rarity.iconMaterial)

                        this@storage.add(button {
                            item = iconItem
                            title = rarity.title
                            description = rarity.description
                            price = rarity.price
                            hint = if (canBuy) "Купить" else "Недостаточно\nсредств"
                            onClick { _, _, _ ->
                                if (!canBuy) return@onClick

                                val worker = WorkerGenerator.generate(rarity)
                                val lootDrop = LootDrop(iconItem, worker.name, rarity.dropRare)
                                OpenWorker(this@user, worker, lootDrop).tryExecute()
                            }
                        })
                    }
                }
            }
        }
    }
}