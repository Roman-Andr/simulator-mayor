package me.slavita.construction.action.command

import me.func.mod.conversation.data.LootDrop
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.app
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.ItemIcons
import me.slavita.construction.worker.WorkerGenerator
import me.slavita.construction.worker.WorkerRarity
import org.bukkit.entity.Player

class LootboxCommand(player: Player) : MenuCommand(player) {

    override fun getMenu(): Openable {
        val storage = mutableListOf<ReactiveButton>()

        app.getUser(player).run {
            WorkerRarity.values().forEach {
                val canBuy = stats.money >= it.price
                val iconItem = ItemIcons.get(it.iconKey, it.iconValue, it.iconMaterial)

                storage.add(
                    ReactiveButton()
                        .item(iconItem)
                        .title(it.title)
                        .description(it.description)
                        .price(it.price)
                        .hint(if (canBuy) "Купить" else "Недостаточно\nсредств")
                        .onClick { _, _, _ ->
                            if (!canBuy) return@onClick

                            val worker = WorkerGenerator.generate(it)
                            val lootDrop = LootDrop(iconItem, worker.name, it.dropRare)
                            OpenWorker(this, worker, lootDrop).tryExecute()
                        }
                )
            }

            return Selection(
                title = "Покупка строителей",
                money = "Ваш баланс ${stats.money}",
                rows = 3,
                columns = 3,
                storage = storage
            )
        }
    }
}