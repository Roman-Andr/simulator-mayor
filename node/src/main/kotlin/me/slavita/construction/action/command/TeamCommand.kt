package me.slavita.construction.action.command

import me.func.mod.ui.menu.Openable
import me.slavita.construction.action.MenuCommand
import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.selection.Selection
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import org.apache.commons.lang.BooleanUtils
import org.bukkit.entity.Player

class TeamCommand(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        val user = app.getUser(player)
        val storage = mutableListOf<ReactiveButton>()

        user.workers.filter { it.active }.sortedBy { it.rarity.price }.reversed()
            .plus(user.workers.filter { !it.active }.sortedBy { it.rarity.price }.reversed().asIterable()).forEach {
                storage.add(
                    ReactiveButton()
                        .item(ItemIcons.get(it.rarity.iconKey, it.rarity.iconValue, it.rarity.iconMaterial))
                        .title(it.name)
                        .hover("§aИмя: ${it.name}\n" +
                                "§eРедкость: ${it.rarity.title}\n" +
                                "§bУровень: ${it.skill}\n" +
                                "§3Надёжность: ${it.reliability}\n" +
                                "§cЖадность: ${it.rapacity.title}")
                        .hint(if (it.active) "Активный" else "Не выбран")
                        .special(it.active)
                        .onClick { _, _, _ ->
                            it.active = !it.active
                            execute()
                        }
                )
            }

        return Selection(
            title = "Ваши строители",
            money = "Ваш баланс ${user.stats.money}",
            rows = 4,
            columns = 5,
            storage = storage
        )
    }
}