package me.slavita.construction.prepare

import me.func.mod.Anime
import me.func.mod.util.after
import me.func.protocol.math.Position
import me.func.protocol.ui.indicator.Indicators
import me.slavita.construction.multichat.MultiChats
import me.slavita.construction.player.User
import me.slavita.construction.ui.ScoreBoardGenerator
import ru.cristalix.core.realm.IRealmService

object UIPrepare : IPrepare {
    override fun prepare(user: User) {
        user.player.setResourcePack("")
        Anime.hideIndicator(user.player, Indicators.HEALTH, Indicators.HUNGER, Indicators.EXP)
        MultiChats.sendPlayerChats(user.player)
        ScoreBoardGenerator.generate(user.player)
    }
}