package me.slavita.construction.prepare

import me.slavita.construction.player.Tags
import me.slavita.construction.player.User
import me.slavita.construction.utils.cristalixName

object TagsPrepare : IPrepare {
    override fun prepare(user: User) {
        user.player.apply {
            playerListName =
                "${cristalixName}${if (user.data.tag != Tags.NONE && user.data.settings.tagShow) " ${user.data.tag.tag}" else ""}"
        }
    }
}