package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.player.User
import ru.cristalix.core.permissions.IPermissionService
import ru.cristalix.core.permissions.StaffGroups

object PermissionsPrepare: Prepare {
    override fun prepare(user: User) {
        if (app.localStaff.contains(user.player.uniqueId)) {
            IPermissionService.get().getPermissionContextDirect(user.player.uniqueId).displayGroup = StaffGroups.LOCAL_STAFF
            user.player.allowFlight = true
        }
    }
}