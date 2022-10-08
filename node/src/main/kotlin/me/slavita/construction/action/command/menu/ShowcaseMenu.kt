package me.slavita.construction.action.command.menu

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.stream.Collectors
import java.util.stream.Stream

class ShowcaseMenu(player: Player, val menuName: String, val items: List<ItemProperties>) : MenuCommand(player) {
    override fun getMenu(): Openable {
        app.getUser(player).run {
            return Selection(
                title = menuName,
                vault = Emoji.DOLLAR,
                money = "Ваш баланс ${stats.money.toMoney()}",
                rows = 6,
                columns = 14,
                storage = mutableListOf<ReactiveButton>().apply storage@{
                    items.forEach { item ->
                        this@storage.add(
                            ReactiveButton()
                                .item(item.createItemStack(1))
                                .hover(
                                    Stream.of(
                                    "${ChatColor.AQUA}Купить 8 шт за 123 [ЛКМ]\n",
                                    "${ChatColor.AQUA}Купить 32 шт за 123 [ПКМ]"
                                ).collect(Collectors.joining()))
                                .hint((if (stats.money >= 123) "${ChatColor.WHITE}" else "${ChatColor.RED}") + Emoji.COIN)
                                .onLeftClick { _, _, _ ->
                                    stats.money -= 123
                                    player.inventory.addItem(item.createItemStack(8))
                                }
                                .onRightClick { _, _, _ ->
                                    stats.money -= 123
                                    player.inventory.addItem(item.createItemStack(32))
                                }
                        )
                    }
                }
            )
        }
    }
}