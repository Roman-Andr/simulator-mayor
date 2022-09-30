package me.slavita.construction.action.command

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import org.bukkit.entity.Player

class TeamCommand(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = "Ваши строители",
                money = "Ваш баланс ${stats.money}",
                rows = 4,
                columns = 5,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    workers.filter { it.active }.sortedBy { it.rarity.price }.reversed()
                        .plus(workers.filter { !it.active }.sortedBy { it.rarity.price }.reversed().asIterable())
                        .forEach {
                            this@storage.add(
                                ReactiveButton()
                                    .item(ItemIcons.get(it.rarity.iconKey, it.rarity.iconValue, it.rarity.iconMaterial))
                                    .title(it.name)
                                    .hover(
                                        "§aИмя: ${it.name}\n" +
                                                "§eРедкость: ${it.rarity.title}\n" +
                                                "§bУровень: ${it.skill}\n" +
                                                "§3Надёжность: ${it.reliability}\n" +
                                                "§cЖадность: ${it.rapacity.title}"
                                    )
                                    .hint(if (it.active) "Активный" else "Не выбран")
                                    .special(it.active)
                                    .onClick { _, _, _ ->
                                        it.active = !it.active
                                        execute()
                                    }
                            )
                        }
                }
            )
        }
    }
}