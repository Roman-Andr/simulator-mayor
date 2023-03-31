package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.utils.runAsync

object CitiesPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            data.cells.forEach {
                runAsync(5 * 20) {
                    it.allocate()
                }
            }
        }
    }
}
