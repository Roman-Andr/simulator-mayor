package me.slavita.construction.action.command.menu

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
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

class ShowcaseMenu(player: Player, val showcase: Showcase) : MenuCommand(player) {
    private val buttons = mutableListOf<ReactiveButton>()
    private val infoButton = button {
        item = ItemIcons.get("other", "info1")
        hover = getInfo()
        hint = " "
    }
    private var selection = Selection()

    override fun getMenu(): Openable {
        buttons.addAll(getButtons())

        user.run user@{
            selection = selection {
                title = showcase.properties.name
                vault = Formatter.moneyIcon
                info = getShowcaseInfo()
                rows = 5
                columns = 14
                money = "Ваш Баланс ${user.data.statistics.money.toMoney()}"
                storage = buttons
            }
            return selection
        }
    }

    private fun getBalance() = "Ваш Баланс ${user.data.statistics.money.toMoney()}"

    private fun getInfo() = """
        ${GREEN}Обновление цен через: ${GOLD}${showcase.updateTime}
    """.trimIndent()

    private fun buyBlocks(user: User, amount: Int, entry: ShowcaseProduct, selection: Selection) {
        if (!user.blocksStorage.hasSpace(amount)) {
            user.player.deny("Недостаточно места на складе")
            Anime.close(user.player)
            return
        }
        user.tryPurchase(entry.price * amount) {
            user.blocksStorage.addItem(entry.item.createItemStack(1), amount)
            user.player.accept("Вы успешно купили блоки")
            Glow.animate(user.player, 0.3, GlowColor.GREEN)
            selection.money = getBalance()
        }
    }

    private fun getButtons(): MutableList<ReactiveButton> {
        return showcase.properties.elements.mapM { entry ->
            val emptyItem = entry.item.createItemStack(1)
            if (user.showcaseMenuTaskId != 0) scheduler.cancelTask(user.showcaseMenuTaskId)
            user.showcaseMenuTaskId = runTimer(0, 20) {
                infoButton.hover = getInfo()
            }
            button {
                item = emptyItem.validate()
                hover = """
                    ${GREEN}${LanguageHelper.getItemDisplayName(emptyItem, user.player)}
                    ${AQUA}Купить 8 шт за ${entry.price * 8} [ЛКМ]
                    ${AQUA}Купить 64 шт за ${entry.price * 64} [ПКМ]
                    На складе: ${BOLD}${
                        user.blocksStorage.blocks.getOrDefault(
                            entry.item,
                            entry.item.createItemStack(0)
                        ).getAmount()
                    }
                """.trimIndent()
                hint = (if (user.canPurchase(entry.price * 8)) "$WHITE" else "$RED") + " "
                onLeftClick { _, _, _ ->
                    buyBlocks(user, 8, entry, selection)
                    updateButtons()
                }
                onRightClick { _, _, _ ->
                    buyBlocks(user, 64, entry, selection)
                    updateButtons()
                }
            }
        }.apply { add(0, infoButton) }
    }

    fun updateButtons() {
        val newButtons = getButtons()
        buttons.forEachIndexed { index, _ ->
            buttons[index].apply {
                onClick = newButtons[index].onClick
                hover = newButtons[index].hover
            }
        }
    }
}