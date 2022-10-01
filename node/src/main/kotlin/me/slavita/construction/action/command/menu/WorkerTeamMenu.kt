package me.slavita.construction.action.command.menu

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import org.bukkit.entity.Player
import java.util.stream.Collectors
import java.util.stream.Stream

class WorkerTeamMenu(player: Player) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = "Ваши строители",
                vault = Emoji.DOLLAR,
                money = "Ваш баланс ${stats.money}",
                rows = 4,
                columns = 5,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    workers.forEach { worker ->
                        this@storage.add(
                            ReactiveButton()
                                .item(ItemIcons.get(worker.rarity.iconKey, worker.rarity.iconValue, worker.rarity.iconMaterial))
                                .title(worker.name)
                                .hover(Stream.of(
                                    "§aИмя: ${worker.name}\n",
                                    "§eРедкость: ${worker.rarity.title}\n",
                                    "§bУровень: ${worker.skill}§f${Emoji.UP}\n",
                                    "§3Надёжность: ${worker.reliability}\n",
                                    "§cЖадность: ${worker.rapacity.title}\n\n",
                                    "§cПродать за ${worker.sellPrice}§f${Emoji.DOLLAR}"
                                ).collect(Collectors.joining()))
                                .hint("§cПродать")
                                .onClick { _, _, _ ->
                                    SellWorkerConfirm(player, worker).tryExecute()
                                }
                        )
                    }
                }
            )
        }
    }
}