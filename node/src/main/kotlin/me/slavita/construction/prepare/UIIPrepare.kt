package me.slavita.construction.prepare

import me.func.mod.Anime
import me.func.protocol.ui.indicator.Indicators
import me.slavita.construction.multichat.MultiChats
import me.slavita.construction.player.User
import me.slavita.construction.ui.ScoreBoardGenerator

object UIIPrepare: IPrepare {
    override fun prepare(user: User) {
        user.player.setResourcePack("")
        Anime.hideIndicator(user.player, Indicators.HEALTH, Indicators.EXP, Indicators.HUNGER)
        MultiChats.sendPlayerChats(user.player)
        ScoreBoardGenerator.generate(user.player)
    }
}