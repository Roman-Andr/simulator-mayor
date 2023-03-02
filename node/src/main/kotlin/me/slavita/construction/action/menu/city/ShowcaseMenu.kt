package me.slavita.construction.action.menu.city

import me.func.mod.Anime
import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.Glow
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.MenuCommand
import me.slavita.construction.city.showcase.Showcase
import me.slavita.construction.city.showcase.ShowcaseProduct
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.SHOWCASE_INFO
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.runTimer
import me.slavita.construction.utils.scheduler
import me.slavita.construction.utils.size
import me.slavita.construction.utils.validate
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.RED
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import org.bukkit.ChatColor.YELLOW
import org.bukkit.entity.Player

class ShowcaseMenu(player: Player, val showcase: Showcase) : MenuCommand(player) {
    private val buttons = mutableListOf<ReactiveButton>()
    private var selection = Selection()

    override fun getMenu(): Openable {
        buttons.addAll(getButtons())

        user.run {
            selection = selection {
                title = showcase.properties.name
                info = SHOWCASE_INFO
                vault = Formatter.moneyIcon
                size(5, 14)
                money = getMoney()
                storage = buttons
            }
            return selection
        }
    }

    private fun getMoney() = """
        Обновление цен через: ${GOLD}${showcase.updateTime}    ${GOLD}Баланс ${user.data.money.toMoney()}
    """.trimIndent()

    private fun buyBlocks(user: User, amount: Int, entry: ShowcaseProduct, selection: Selection) {
        user.run {
            val hasSpace = player.inventory.firstEmpty() != -1
            if (!hasSpace && !data.blocksStorage.hasSpace(amount)) {
                player.deny("Нехватает места на складе!")
                Anime.close(player)
                return
            }
            tryPurchase(entry.price * amount) {
                player.accept("Вы успешно купили блоки")
                data.addBlocks(amount)
                if (hasSpace) player.inventory.addItem(entry.item.createItemStack(amount))
                else data.blocksStorage.addItem(entry.item.createItemStack(1), amount)
                Glow.animate(player, 0.3, GlowColor.GREEN)
                selection.money = getMoney()
            }
        }
    }

    private fun getButtons(): MutableList<ReactiveButton> {
        return showcase.properties.elements.sortedBy { it.item.type.id }.mapM { entry ->
            val emptyItem = entry.item.createItemStack(1)
            if (user.showcaseMenuTaskId != 0) scheduler.cancelTask(user.showcaseMenuTaskId)
            user.showcaseMenuTaskId = runTimer(0, 20) {
                selection.money = getMoney()
            }
            button {
                item = emptyItem.validate()
                hover = """
                    ${GREEN}${LanguageHelper.getItemDisplayName(emptyItem, user.player)}
                    ${WHITE}Купить ${AQUA}8 шт ${WHITE}за ${(entry.price * 8).toMoneyIcon()} ${YELLOW}[ ЛКМ ]
                    ${WHITE}Купить ${AQUA}64 шт ${WHITE}за ${(entry.price * 64).toMoneyIcon()} ${YELLOW}[ ПКМ ]
                    
                    ${WHITE}На складе: ${AQUA}${user.data.blocksStorage.blocks.getOrDefault(entry.item, 0)} шт
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
        }
    }

    fun updateButtons() {
        val newButtons = getButtons()
        buttons.forEachIndexed { index, _ ->
            buttons[index].run {
                onClick = newButtons[index].onClick
                hover = newButtons[index].hover
            }
        }
    }
}
