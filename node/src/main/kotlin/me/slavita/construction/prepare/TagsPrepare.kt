package me.slavita.construction.prepare

import me.slavita.construction.player.Tags
import me.slavita.construction.player.User
import me.slavita.construction.utils.CristalixUtil
import org.bukkit.ChatColor.*

object TagsPrepare : IPrepare {
    override fun prepare(user: User) {
        user.player.apply {
            playerListName = "${CristalixUtil.getDisplayName(this)}${if (user.tag != Tags.NONE) "${DARK_GRAY}[${user.tag.tag}${DARK_GRAY}]" else ""}"
        }
    }
}