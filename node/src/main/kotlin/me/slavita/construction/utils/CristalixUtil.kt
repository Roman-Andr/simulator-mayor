package me.slavita.construction.utils

import org.bukkit.entity.Player
import ru.cristalix.core.account.IAccountService
import ru.cristalix.core.permissions.IPermissionContext
import ru.cristalix.core.permissions.IPermissionService
import java.util.*

object CristalixUtil {
    val Player.cristalixName
        get() = getDisplayName(this.uniqueId)

    fun getDisplayName(uuid: UUID): String {
        val name = IAccountService.get().getNameByUuid(uuid).get()
        return getDisplayNameFromContext(IPermissionService.get().getPermissionContextDirect(uuid), name)
    }

    private fun getDisplayNameFromContext(context: IPermissionContext, name: String): String {
        val group = context.displayGroup
        val color = if (context.color == null) "" else context.color
        val prefix =
            if (context.customProfile.chatPrefix != null) context.customProfile.chatPrefix else (group.prefixColor + group.prefix)
        return ((if (prefix.isNotEmpty()) "$prefix " else "") + group.nameColor) + color + name
    }
}