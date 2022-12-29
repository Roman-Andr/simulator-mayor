package me.slavita.construction.prepare

import me.slavita.construction.action.command.menu.DailyMenu
import me.slavita.construction.common.utils.TimeFormatter
import me.slavita.construction.player.User

object DailyRewardsPrepare : IPrepare {
    private const val DAY = 24 * 60 * 60 * 1000

    override fun prepare(user: User) {
        user.data.statistics.apply {
            val now = System.currentTimeMillis()
            if (nextTakeDailyReward > now) return
            if (nextDay == 7) nextDay = 0
            DailyMenu(user.player).tryExecute()
        }
    }
}