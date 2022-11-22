package me.slavita.construction.booster

import me.func.stronghold.Stronghold
import me.func.stronghold.booster.BoosterGlobal
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.utils.CristalixUtil
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.user
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

object Boosters {
    init {
        Stronghold.addThanksConsumer { owner, player ->
            if (owner != null) {
                owner.killboard("Вас поблагодарил игрок ${CristalixUtil.getDisplayName(owner)}")
                owner.user.stats.money += 100
            }
            if (player != null) {
                player.killboard("Вы поблагодарили игрока ${CristalixUtil.getDisplayName(player)}")
                player.user.stats.money += 100
            }
        }

        Stronghold.onActivate {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.user.run {
                    stats.speed.apply { applyBoosters(BoosterType.SPEED_BOOSTER) }
                    player?.walkSpeed = stats.speed
                }
            }
        }

        Stronghold.onExpire {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.user.run {
                    stats.speed.apply {
                        applyBoosters(BoosterType.SPEED_BOOSTER)
                    }
                    player?.walkSpeed = stats.speed
                }
            }
        }
    }

    fun activateGlobal(user: User, time: Long, unit: TimeUnit, vararg boosters: BoosterType) {
        Stronghold.activateBoosters(
            *boosters.map {
                BoosterGlobal.builder()
                    .type(it.label)
                    .title(it.title)
                    .owner(user.player)
                    .owner(CristalixUtil.getDisplayName(user.player))
                    .duration(time, unit)
                    .multiplier(1.5)
                    .maxStackable(4)
                    .build()
            }.toTypedArray()
        )
    }
}