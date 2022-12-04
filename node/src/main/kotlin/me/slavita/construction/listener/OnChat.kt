package me.slavita.construction.listener

import me.slavita.construction.utils.listener
import me.slavita.construction.utils.user
import net.md_5.bungee.api.chat.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor.*
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerChatEvent
import ru.cristalix.core.multichat.ChatMessage
import ru.cristalix.core.multichat.IMultiChatService
import ru.cristalix.core.permissions.IPermissionService

object OnChat {
    init {
        listener<AsyncPlayerChatEvent> {
            isCancelled = true
            println("<" + player.displayName + ">: " + message)

            Bukkit.getOnlinePlayers().forEach {
                it.sendMessage(*getComponents(player, message).toTypedArray())
            }

            IMultiChatService.get()
                .sendMessage("construction", ChatMessage.create("slvt", "construction", getComponents(player, message).toTypedArray()))
        }
    }

    private fun getComponents(player: Player, message: String): ArrayList<BaseComponent> {

        val displayName = IPermissionService.get().getPermissionContextDirect(player.uniqueId).toDisplayName()
        val chatColor = IPermissionService.get().getPermissionContextDirect(player.uniqueId).chatColor
        val tag = player.user.tag

        var coloredMessage = " "
        message.forEach {
            coloredMessage += chatColor + it
        }

        val nameComponent = ComponentBuilder(displayName).create().first()
        nameComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/controls ${player.displayName}")
        nameComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("Быстрые действия с игроком").create())

        val tagComponent = ComponentBuilder(if (tag.tag.isEmpty()) "" else " " + tag.tag).create().first()
        tagComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/settag ${tag.name}")
        tagComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("${GREEN}Применить тэг ${RESET}${tag.tag} ${GRAY}[${GOLD}ПКМ${GRAY}]").create())
        val mergeComponent = ComponentBuilder(" §8»").create().first()
        val messageComponent = ComponentBuilder(coloredMessage).create().first()

        return arrayListOf(nameComponent, tagComponent, mergeComponent, messageComponent)
    }
}