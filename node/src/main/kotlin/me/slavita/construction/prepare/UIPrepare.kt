package me.slavita.construction.prepare

import me.func.mod.Anime
import me.func.protocol.ui.indicator.Indicators
import me.slavita.construction.multichat.MultiChats
import me.slavita.construction.player.User
import me.slavita.construction.ui.ScoreboardGenerator
import me.slavita.construction.ui.Texture

object UIPrepare : IPrepare {
    override fun prepare(user: User) {
        user.player.setResourcePack("")
        Anime.hideIndicator(user.player, Indicators.HEALTH, Indicators.HUNGER, Indicators.EXP)
        MultiChats.sendPlayerChats(user.player)
        ScoreboardGenerator.generate(user.player)
        Texture.values().forEach {
            Anime.loadTexture(
                user.player,
                "https://storage.c7x.dev/${System.getProperty("storage.user")}/construction/${it.fileName}"
            )
        }
    }
}