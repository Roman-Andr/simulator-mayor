package me.slavita.construction.action.command.menu

import me.func.mod.ui.menu.Openable
import me.func.mod.ui.menu.button
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.MenuCommand
import me.slavita.construction.player.Tags
import me.slavita.construction.prepare.TagsPrepare
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.ui.menu.ItemIcons
import me.slavita.construction.ui.menu.MenuInfo
import me.slavita.construction.ui.menu.StatsType
import me.slavita.construction.utils.*
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.entity.Player

class TagsMenu(player: Player) : MenuCommand(player) {
    override fun getMenu(): Openable {
        player.user.data.let { data ->
            return getBaseSelection(MenuInfo("${AQUA}${BOLD}Меню тегов", StatsType.MONEY, 4, 4)).apply {
                info = getTagsInfo()
                storage = Tags.values().mapM { tag ->
                    button {
                        title = if (tag.tag == "") "Пустой" else tag.tag
                        if (data.ownTags.contains(tag)) {
                            hint = if (data.tag == tag) "Выбран" else "Выбрать"
                            item = ItemIcons.get("other", "pets1", data.tag == tag)
                            backgroundColor = if (data.tag != tag) GlowColor.GREEN else GlowColor.BLUE
                            click { _, _, _ ->
                                if (data.tag != tag) {
                                    data.tag = tag
                                    TagsPrepare.prepare(player.user)
                                    TagsMenu(player).tryExecute()
                                }
                            }
                        } else {
                            hint = "Купить"
                            item = ItemIcons.get("other", "lock")
                            backgroundColor = GlowColor.NEUTRAL
                            description = tag.price.toMoneyIcon()
                            click { _, _, _ ->
                                player.user.tryPurchase(tag.price) {
                                    data.ownTags.add(tag)
                                }
                                TagsMenu(player).tryExecute()
                            }
                        }
                    }
                }
            }
        }
    }
}
