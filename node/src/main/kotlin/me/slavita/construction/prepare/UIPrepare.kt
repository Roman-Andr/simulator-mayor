package me.slavita.construction.prepare

import me.func.mod.Anime
import me.func.protocol.ui.indicator.Indicators
import me.slavita.construction.common.utils.LoadingState
import me.slavita.construction.player.User
import me.slavita.construction.ui.ScoreboardGenerator
import me.slavita.construction.ui.Texture
import me.slavita.construction.utils.STORAGE_URL
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.sendLoadingState

object UIPrepare : IPrepare {
    override fun prepare(user: User) {
        user.player.setResourcePack("", "")
        Anime.hideIndicator(user.player, Indicators.HEALTH, Indicators.HUNGER, Indicators.EXP, Indicators.HOT_BAR)
        ScoreboardGenerator.generate(user)
        Texture.values().forEach {
            Anime.loadTexture(
                user.player,
                "$STORAGE_URL/images/${it.fileName}"
            )
        }

        user.player.sendLoadingState(LoadingState.STRUCTURES)
        runAsync(140) {
            user.player.sendLoadingState(LoadingState.FINISHED)
            runAsync(40) {
                runAsync(20) {
                    DailyRewardsPrepare.prepare(user)
                }
                Anime.showIndicator(user.player, Indicators.HOT_BAR)
            }
        }

        runAsync { user.updateCriBalance() }
    }
}
