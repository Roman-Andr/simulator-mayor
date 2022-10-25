package me.slavita.construction.action.command.menu

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.stream.Collectors
import java.util.stream.Stream

class ShowcaseMenu(player: Player, val menuName: String, val items: List<ItemProperties>) : MenuCommand(player) {
	override fun getMenu(): Openable {
		app.getUser(player).run {
			return getBaseSelection(MenuInfo(menuName, StatsType.MONEY, 6, 14)).apply {
				storage = mutableListOf<ReactiveButton>().apply storage@{
					items.forEach { targetItem ->
						this@storage.add(
							button {
								item = targetItem.createItemStack(1)
								hover =
									Stream.of(
										"${ChatColor.AQUA}Купить 8 шт за 123 [ЛКМ]\n",
										"${ChatColor.AQUA}Купить 32 шт за 123 [ПКМ]"
									).collect(Collectors.joining())
								hint = (if (canPurchase(123)) "${ChatColor.WHITE}" else "${ChatColor.RED}") + Emoji.COIN
								onLeftClick { _, _, _ ->
									tryPurchase(123, { player.inventory.addItem(targetItem.createItemStack(8)) })
								}
								onRightClick { _, _, _ ->
									tryPurchase(123, { player.inventory.addItem(targetItem.createItemStack(32)) })
								}
							}
						)
					}
				}
			}
		}
	}
}