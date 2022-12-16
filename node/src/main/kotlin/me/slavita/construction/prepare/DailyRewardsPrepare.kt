package me.slavita.construction.prepare

import me.slavita.construction.action.command.menu.DailyMenu
import me.slavita.construction.player.User

object DailyRewardsPrepare : IPrepare {
    private const val DAY = 24 * 60 * 60 * 1000

    override fun prepare(user: User) {
        val nextTake = user.data.statistics.nextTakeDailyReward
        val now = System.currentTimeMillis()
        if (nextTake > now) return
        if (user.data.statistics.nextDay == 7 || nextTake + DAY < now) user.data.statistics.nextDay = 0
        val nextDay = user.data.statistics.nextDay
        user.data.statistics.nextDay = nextDay - 1
        DailyMenu(user.player).tryExecute()
    }
}