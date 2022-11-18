package me.slavita.construction.prepare

import me.func.mod.Anime
import me.func.protocol.ui.indicator.Indicators
import me.slavita.construction.multichat.MultiChats
import me.slavita.construction.player.User
import me.slavita.construction.ui.ScoreBoardGenerator

object UIPrepare : IPrepare {
    override fun prepare(user: User) {
        user.player.setResourcePack("")
        user.updateLevelBar()
        Anime.hideIndicator(user.player, Indicators.HEALTH, Indicators.HUNGER)
        MultiChats.sendPlayerChats(user.player)
        ScoreBoardGenerator.generate(user.player)
    }
}