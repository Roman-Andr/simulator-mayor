package me.slavita.construction.prepare

import me.slavita.construction.player.Tags
import me.slavita.construction.player.User
import me.slavita.construction.utils.CristalixUtil
import org.bukkit.ChatColor.DARK_GRAY

object TagsPrepare : IPrepare {
    override fun prepare(user: User) {
        user.player.apply {
            playerListName =
                "${CristalixUtil.getDisplayName(this)}${if (user.data.tag != Tags.NONE && user.data.settings.tagShow) " ${DARK_GRAY}[${user.data.tag.tag}${DARK_GRAY}]" else ""}"
        }
    }
}