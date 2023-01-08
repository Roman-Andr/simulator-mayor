package me.slavita.construction.prepare

import me.func.mod.Anime
import me.slavita.construction.player.User

object WarningPrepare : IPrepare {
    override fun prepare(user: User) {
        if (user.data.hasFreelance) {
            user.data.hasFreelance = false
            user.data.statistics.reputation -= 100
            Anime.alert(user.player, "Вы вышли в прошлый раз и вам бан короче поэтому -100 к репутации")
        }
    }
}
