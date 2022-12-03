package me.slavita.construction.action.command.menu

import me.func.mod.ui.Glow
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.ui.Formatter
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.utils.user
import me.slavita.construction.world.ItemProperties
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player
import ru.cristalix.core.formatting.Formatting.fine

class ShowcaseMenu(player: Player, val menuName: String, val items: HashSet<Pair<ItemProperties, Long>>) :
    MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.run user@{
            return selection {
                title = menuName
                vault = Formatter.moneyIcon
                rows = 5
                columns = 14
                money = "Ваш Баланс ${player.user.statistics.money.toMoney()}"
                storage = items.map { targetItem ->
                    val emptyItem = targetItem.first.createItemStack(1)
                    button {
                        item = emptyItem
                        hover = """
                            ${AQUA}Купить 8 шт за ${targetItem.second * 8} [ЛКМ]
                            ${AQUA}Купить 32 шт за ${targetItem.second * 32} [ПКМ]
                        """.trimIndent()
                        hint = (if (canPurchase(123)) "$WHITE" else "$RED") + Emoji.COIN
                        onLeftClick { _, _, _ ->
                            tryPurchase(123, {
                                this@user.blocksStorage.addItem(emptyItem, 8)
                                player.playSound(MusicSound.LEVEL_UP)
                                player.killboard(fine("Вы успешно купили блоки"))
                                this@selection.money = getBalance()
                                Glow.animate(player, 0.3, GlowColor.GREEN)
                            })
                        }
                        onRightClick { _, _, _ ->
                            tryPurchase(123, {
                                this@user.blocksStorage.addItem(emptyItem, 32)
                                player.playSound(MusicSound.LEVEL_UP)
                                player.killboard(fine("Вы успешно купили блоки"))
                                this@selection.money = getBalance()
                                Glow.animate(player, 0.3, GlowColor.GREEN)
                            })
                        }
                    }
                }.toMutableList()
            }
        }
    }

    fun getBalance() = "Ваш Баланс ${player.user.statistics.money.toMoney()}"
}