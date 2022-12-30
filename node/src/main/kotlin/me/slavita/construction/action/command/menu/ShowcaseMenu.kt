package me.slavita.construction.action.command.menu

import me.func.mod.Anime
import me.func.mod.ui.Glow
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.player.User
import me.slavita.construction.showcase.Showcase
import me.slavita.construction.showcase.ShowcaseProduct
import me.slavita.construction.ui.Formatter
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.*
import me.slavita.construction.utils.language.LanguageHelper
import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class ShowcaseMenu(player: Player, val showcase: Showcase) :
    MenuCommand(player) {
    companion object {
        var updateTaskId = 0
    }

    override fun getMenu(): Openable {
        val infoButton = button {
            item = ItemIcons.get("other", "info1")
            hover = getInfo()
            hint = " "
        }

        player.user.run user@{
            return selection {
                title = showcase.properties.name
                vault = Formatter.moneyIcon
                info = getShowcaseInfo()
                rows = 5
                columns = 14
                money = "Ваш Баланс ${player.user.data.statistics.money.toMoney()}"
                storage = showcase.properties.elements.mapM { entry ->
                    val emptyItem = entry.item.createItemStack(1)
                    if (updateTaskId != 0) Bukkit.server.scheduler.cancelTask(updateTaskId)
                    updateTaskId = runTimer(0, 20) {
                        infoButton.hover = getInfo()
                    }
                    button {
                        item = emptyItem.validate()
                        hover = """
                            ${GREEN}${LanguageHelper.getItemDisplayName(emptyItem, player)}
                            ${AQUA}Купить 8 шт за ${entry.price * 8} [ЛКМ]
                            ${AQUA}Купить 64 шт за ${entry.price * 64} [ПКМ]
                            На складе: ${BOLD}${
                            player.user.blocksStorage.blocks.getOrDefault(
                                entry.item,
                                entry.item.createItemStack(0)
                            ).getAmount()
                        }
                        """.trimIndent()
                        hint = (if (canPurchase(entry.price * 8)) "$WHITE" else "$RED") + " "
                        onLeftClick { _, _, _ ->
                            buyBlocks(this@user, 8, entry, this@selection)
                        }
                        onRightClick { _, _, _ ->
                            buyBlocks(this@user, 64, entry, this@selection)
                        }
                    }
                }.apply { add(0, infoButton) }
            }
        }
    }

    private fun getBalance() = "Ваш Баланс ${player.user.data.statistics.money.toMoney()}"

    private fun getInfo() = """
        ${GREEN}Обновление цен через: ${GOLD}${showcase.updateTime}
    """.trimIndent()

    private fun buyBlocks(user: User, amount: Int, entry: ShowcaseProduct, selection: Selection) {
        if (!user.blocksStorage.hasSpace(amount)) {
            player.accept("Недостаточно места на складе")
            Anime.close(player)
            return
        }
        user.tryPurchase(entry.price * amount) {
            user.blocksStorage.addItem(entry.item.createItemStack(1), amount)
            player.accept("Вы успешно купили блоки")
            Glow.animate(player, 0.3, GlowColor.GREEN)
            selection.money = getBalance()
        }
    }
}