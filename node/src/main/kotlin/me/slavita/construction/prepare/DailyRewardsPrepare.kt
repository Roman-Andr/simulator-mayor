package me.slavita.construction.prepare

import me.slavita.construction.action.command.menu.DailyMenu
import me.slavita.construction.player.User

object DailyRewardsPrepare : IPrepare {
    private const val DAY = 24 * 60 * 60 * 1000

    override fun prepare(user: User) {
        val nextTake = user.data.nextTakeDailyReward
        val now = System.currentTimeMillis()
        if (nextTake > now) return
        val nextDay = user.data.nextDay
        if (user.data.nextDay == 7 || nextTake + DAY < now) user.data.nextDay = 0
        user.data.nextDay = 5
        DailyMenu(user.player).tryExecute()
    }
}
