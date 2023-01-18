package me.slavita.construction.action.command.menu.general

import me.func.mod.reactive.ReactiveButton
import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection.Selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.dontate.Donates
import me.slavita.construction.dontate.TagDonate
import me.slavita.construction.player.Tags
import me.slavita.construction.prepare.TagsPrepare
import me.slavita.construction.ui.Formatter.toCriMoney
import me.slavita.construction.ui.Formatter.toMoney
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.*
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class TagsMenu(val player: Player) : MenuCommand(player) {
    private val buttons = mutableListOf<ReactiveButton>()
    private var selection = Selection()

    override fun getMenu(): Openable {
        buttons.addAll(getButtons())

        selection = getBaseSelection(MenuInfo("${AQUA}${BOLD}Меню тегов", StatsType.MONEY, 4, 4), user).apply {
            info = getTagsInfo()
            storage = buttons
        }
        return selection
    }

    private fun updateButtons() {
        val newButtons = getButtons()
        buttons.forEachIndexed { index, _ ->
            buttons[index].apply {
                item = newButtons[index].item
                onClick = newButtons[index].onClick
                hint = newButtons[index].hint
                backgroundColor = newButtons[index].backgroundColor
            }
        }
    }

    private fun getButtons(): MutableList<ReactiveButton> {
        val data = user.data
        return Tags.values().filter { !it.donate }.mapM { tag ->
            button {
                title = if (tag.tag == "") "Пустой" else tag.tag
                if (data.ownTags.contains(tag)) {
                    hint = if (data.tag == tag) "Выбран" else "Выбрать"
                    item = ItemIcons.get("other", "pets1", data.tag == tag)
                    backgroundColor = if (user.data.tag != tag) GlowColor.GREEN else GlowColor.BLUE
                    onClick { _, _, _ ->
                        if (data.tag != tag) {
                            data.tag = tag
                            TagsPrepare.prepare(user)
                            updateButtons()
                        }
                    }
                } else {
                    hint = "Купить"
                    item = ItemIcons.get("other", "lock")
                    backgroundColor = GlowColor.NEUTRAL
                    description = tag.price.toMoneyIcon()
                    onClick { _, _, _ ->
                        user.tryPurchase(tag.price) {
                            data.ownTags.add(tag)
                            selection.money = "Ваш баланс ${user.data.statistics.money.toMoney()}"
                        }
                        updateButtons()
                    }
                }
            }
        }.apply {
            addAll(Donates.values().filter { it.donate is TagDonate }.map {
                button {
                    val tag = (it.donate as TagDonate).tag
                    item = ItemIcons.get("other", "pets1", data.tag == tag)
                    title = it.donate.tag.tag
                    if (!data.ownTags.contains(tag)) {
                        hint = "Купить"
                        hover = it.donate.description
                        description = "Цена: ${it.donate.price.toCriMoney()}"
                        backgroundColor = GlowColor.BLUE_MIDDLE
                        onClick { _, _, _ ->
                            it.donate.purchase(player.user)
                        }
                    } else {
                        hint = if (data.tag == tag) "Выбран" else "Выбрать"
                        backgroundColor = if (data.tag == tag) GlowColor.BLUE_LIGHT else GlowColor.BLUE
                        onClick { _, _, _ ->
                            if (data.tag != tag) {
                                data.tag = tag
                                TagsPrepare.prepare(user)
                                updateButtons()
                            }
                        }
                    }
                }
            })
        }
    }
}