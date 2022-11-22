package me.slavita.construction.booster

import me.func.stronghold.Stronghold
import me.func.stronghold.booster.BoosterGlobal
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.utils.CristalixUtil
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

object Boosters {
    init {
        Stronghold.addThanksConsumer { owner, player ->
            if (owner != null) {
                owner.killboard("Вас поблагодарил игрок ${CristalixUtil.getDisplayName(owner)}")
                app.getUser(owner).stats.money += 100
            }
            if (player != null) {
                player.killboard("Вы поблагодарили игрока ${CristalixUtil.getDisplayName(player)}")
                app.getUser(player).stats.money += 100
            }
        }

        Stronghold.onActivate {
            Bukkit.getOnlinePlayers().forEach { player ->
                app.getUser(player).run {
                    stats.speed.apply { applyBoosters(BoosterType.SPEED_BOOSTER) }
                    player?.walkSpeed = stats.speed
                }
            }
        }

        Stronghold.onExpire {
            Bukkit.getOnlinePlayers().forEach { player ->
                app.getUser(player).run {
                    stats.speed.apply {
                        applyBoosters(BoosterType.SPEED_BOOSTER)
                    }
                    player?.walkSpeed = stats.speed
                }
            }
        }
    }

    fun activateGlobal(player: Player, booster: BoosterType) {
        Stronghold.activateBoosters(
            BoosterGlobal.builder()
                .type(booster.label)
                .title(booster.title)
                .owner(player)
                .owner(CristalixUtil.getDisplayName(player))
                .duration(30, TimeUnit.SECONDS)
                .multiplier(1.5)
                .maxStackable(4)
                .build()
        )
    }
}