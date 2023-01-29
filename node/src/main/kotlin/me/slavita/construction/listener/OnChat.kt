package me.slavita.construction.listener

import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.utils.listener
import me.slavita.construction.utils.log
import me.slavita.construction.utils.user
import net.md_5.bungee.api.chat.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor.DARK_GRAY
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerChatEvent
import ru.cristalix.core.multichat.ChatMessage
import ru.cristalix.core.multichat.IMultiChatService
import ru.cristalix.core.permissions.IPermissionService
import ru.cristalix.core.realm.IRealmService

object OnChat : IRegistrable {
    override fun register() {
        listener<AsyncPlayerChatEvent> {
            isCancelled = true
            log("<" + player.displayName + ">: " + message)

            Bukkit.getOnlinePlayers().forEach {
                it.sendMessage(*getComponents(player, message).toTypedArray())
            }

            IMultiChatService.get()
                .sendMessage(
                    "construction",
                    ChatMessage.create("slvt", "construction", getComponents(player, message).toTypedArray())
                )
        }
    }

    private fun getComponents(player: Player, message: String): ArrayList<BaseComponent> {

        val displayName = IPermissionService.get().getPermissionContextDirect(player.uniqueId).toDisplayName()
        val chatColor = IPermissionService.get().getPermissionContextDirect(player.uniqueId).chatColor
        val tag = player.user.data.tag

        var coloredMessage = " "
        message.forEach {
            coloredMessage += chatColor + it
        }

        return arrayListOf(
            ComponentBuilder(displayName).create().first().apply {
                clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/controls ${player.displayName}")
                hoverEvent =
                    HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("Быстрые действия с игроком").create())
            },
            ComponentBuilder(if (tag.tag.isEmpty()) "" else " " + tag.tag).create().first(),
            ComponentBuilder(" ${DARK_GRAY}»").create().first(),
            ComponentBuilder(coloredMessage).create().first().apply {
                hoverEvent = HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    ComponentBuilder("Игрок пишет с сервера ${IRealmService.get().currentRealmInfo.realmId.realmName}").create()
                )
            }
        )
    }
}