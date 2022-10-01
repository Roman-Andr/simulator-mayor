package me.slavita.construction.action.command.menu

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.OpenCommand
import me.slavita.construction.app
import me.slavita.construction.ui.ItemIcons
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.stream.Collectors
import java.util.stream.Stream

class MarketStandMenu(player: Player, val blocks: Set<Material>) : OpenCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = "Блоки",
                vault = Emoji.DOLLAR,
                money = "Ваш баланс ${stats.money}",
                rows = 6,
                columns = 14,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    blocks.forEach { block ->
                        this@storage.add(
                            ReactiveButton()
                                .item(ItemIcons.get("", "", block))
                                .hover(Stream.of(
                                    "§aКупить 1 шт за 123 [ЛКМ]\n",
                                    "§eКупить 32 шт за 123 [ПКМ]"
                                ).collect(Collectors.joining()))
                                .hint((if (stats.money >= 123) "§f" else "§c") + Emoji.COIN)
                                .onLeftClick { _, _, _ ->
                                    stats.money -= 123
                                    player.inventory.addItem(ItemStack(block, 1))
                                }
                                .onRightClick { _, _, _ ->
                                    stats.money -= 123
                                    player.inventory.addItem(ItemStack(block, 32))
                                }
                        )
                    }
                }
            )
        }
    }
}