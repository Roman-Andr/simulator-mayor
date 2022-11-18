package me.slavita.construction.utils

import org.bukkit.entity.Player
import ru.cristalix.core.account.IAccountService
import ru.cristalix.core.permissions.IPermissionService
import java.util.*

object CristalixUtil {
    fun getDisplayName(player: Player): String {
        return getDisplayName(player.uniqueId)
    }

    private fun getDisplayName(uuid: UUID): String {
        val name = IAccountService.get().getNameByUuid(uuid).get()
        val context = IPermissionService.get().getPermissionContextDirect(uuid)
        val group = context.bestGroup
        val color = if (context.color == null) "" else context.color
        val prefix = group.prefix
        return (group.prefixColor + (if (prefix.isNotEmpty()) "$prefix §8§l| " else "") + group.nameColor) + color + name
    }
}