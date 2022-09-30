package me.slavita.construction.action.command

import me.func.mod.conversation.data.LootDrop
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.entity.Player

class LootboxCommand(player: Player) : MenuCommand(player) {

    override fun getMenu(): Openable {
        app.getUser(player).run user@ {
            return Selection(
                title = "Покупка строителей",
                money = "Ваш баланс ${stats.money}",
                rows = 3,
                columns = 3,
                storage = mutableListOf<ReactiveButton>().apply storage@ {
                    WorkerRarity.values().forEach { rarity ->
                        val canBuy = stats.money >= rarity.price
                        val iconItem = ItemIcons.get(rarity.iconKey, rarity.iconValue, rarity.iconMaterial)

                        this@storage.add(
                            ReactiveButton()
                                .item(iconItem)
                                .title(rarity.title)
                                .description(rarity.description)
                                .price(rarity.price)
                                .hint(if (canBuy) "Купить" else "Недостаточно\nсредств")
                                .onClick { _, _, _ ->
                                    if (!canBuy) return@onClick

                                    val worker = WorkerGenerator.generate(rarity)
                                    val lootDrop = LootDrop(iconItem, worker.name, rarity.dropRare)
                                    OpenWorker(this@user, worker, lootDrop).tryExecute()
                                }
                        )
                    }
                }
            )
        }
    }
}