package me.slavita.construction.action.command.menu

import me.func.mod.ui.Glow
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.market.showcase.Showcase
import me.slavita.construction.ui.Formatter
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.PlayerExtensions.accept
import me.slavita.construction.utils.language.LanguageHelper
import me.slavita.construction.utils.mapM
import me.slavita.construction.utils.user
import me.slavita.construction.utils.validate
import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player

class ShowcaseMenu(player: Player, val showcase: Showcase) :
    MenuCommand(player) {
    companion object {
        var lastTaskId = 0
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
                rows = 5
                columns = 14
                money = "Ваш Баланс ${player.user.data.statistics.money.toMoney()}"
                storage = showcase.properties.elements.mapM { targetItem ->
                    val emptyItem = targetItem.first.createItemStack(1)
                    if (lastTaskId != 0) Bukkit.server.scheduler.cancelTask(lastTaskId)
                    lastTaskId = Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
                        infoButton.hover = getInfo()
                    }, 0L, 20L)
                    button {
                        item = emptyItem.validate()
                        hover = """
                            ${GREEN}${LanguageHelper.getItemDisplayName(emptyItem, player)}
                            ${AQUA}Купить 8 шт за ${targetItem.second * 8} [ЛКМ]
                            ${AQUA}Купить 64 шт за ${targetItem.second * 64} [ПКМ]
                        """.trimIndent()
                        hint = (if (canPurchase(targetItem.second * 8)) "$WHITE" else "$RED") + " "
                        onLeftClick { _, _, _ ->
                            tryPurchase(targetItem.second * 8) {
                                this@user.blocksStorage.addItem(emptyItem, 8)
                                player.accept("Вы успешно купили блоки")
                                this@selection.money = getBalance()
                                Glow.animate(player, 0.3, GlowColor.GREEN)
                            }
                        }
                        onRightClick { _, _, _ ->
                            tryPurchase(targetItem.second * 64) {
                                this@user.blocksStorage.addItem(emptyItem, 32)
                                player.accept("Вы успешно купили блоки")
                                this@selection.money = getBalance()
                                Glow.animate(player, 0.3, GlowColor.GREEN)
                            }
                        }
                    }
                }.apply { add(0, infoButton) }
            }
        }
    }

    private fun getBalance() = "Ваш Баланс ${player.user.data.statistics.money.toMoney()}"

    private fun getInfo() = """
        ${GREEN}Обновление цен через: ${GOLD}время
    """.trimIndent()
}