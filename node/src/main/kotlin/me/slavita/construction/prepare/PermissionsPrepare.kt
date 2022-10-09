package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.player.User
import ru.cristalix.core.permissions.IPermissionService
import ru.cristalix.core.tab.ITabService

object PermissionsPrepare: Prepare {
    override fun prepare(user: User) {
        if (app.localStaff.contains(user.player.uniqueId)) {
            IPermissionService.get().getPermissionContextDirect(user.player.uniqueId).apply {
                permissions.add("*")
            }
            ITabService.get().update(user.player)
            user.player.allowFlight = true
        }
    }
}