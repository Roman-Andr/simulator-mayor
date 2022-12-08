package me.slavita.construction.action.command.menu

import me.func.mod.ui.Glow
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.app
import me.slavita.construction.market.showcase.Showcase
import me.slavita.construction.ui.Formatter
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.Formatter.toTime
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.utils.user
import me.slavita.construction.world.ItemProperties
import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player
import ru.cristalix.core.formatting.Formatting.fine

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
                money = "Ваш Баланс ${player.user.statistics.money.toMoney()}"
                storage = showcase.properties.elements.map { targetItem ->
                    val emptyItem = targetItem.first.createItemStack(1)
                    if (lastTaskId != 0) Bukkit.server.scheduler.cancelTask(lastTaskId)
                    lastTaskId = Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
                        infoButton.hover = getInfo()
                    }, 0L, 20L)
                    button {
                        item = emptyItem
                        hover = """
                            ${AQUA}Купить 8 шт за ${targetItem.second * 8} [ЛКМ]
                            ${AQUA}Купить 64 шт за ${targetItem.second * 64} [ПКМ]
                        """.trimIndent()
                        hint = (if (canPurchase(targetItem.second * 8)) "$WHITE" else "$RED") + " "
                        onLeftClick { _, _, _ ->
                            tryPurchase(targetItem.second * 8, {
                                this@user.blocksStorage.addItem(emptyItem, 8)
                                player.playSound(MusicSound.LEVEL_UP)
                                player.killboard(fine("Вы успешно купили блоки"))
                                this@selection.money = getBalance()
                                Glow.animate(player, 0.3, GlowColor.GREEN)
                            })
                        }
                        onRightClick { _, _, _ ->
                            tryPurchase(targetItem.second * 64, {
                                this@user.blocksStorage.addItem(emptyItem, 32)
                                player.playSound(MusicSound.LEVEL_UP)
                                player.killboard(fine("Вы успешно купили блоки"))
                                this@selection.money = getBalance()
                                Glow.animate(player, 0.3, GlowColor.GREEN)
                            })
                        }
                    }
                }.toMutableList().apply { add(0, infoButton) }
            }
        }
    }

    private fun getBalance() = "Ваш Баланс ${player.user.statistics.money.toMoney()}"

    private fun getInfo() = """
        ${GREEN}Обновление цен через: ${GOLD}${player.user.showcaseUpdateTimeLast.toTime()}
    """.trimIndent()
}