package me.slavita.construction.booster

import me.func.stronghold.Stronghold
import me.func.stronghold.booster.BoosterGlobal
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.player.User
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.cristalixName
import me.slavita.construction.utils.user
import org.bukkit.Bukkit
import java.util.concurrent.TimeUnit

object Boosters : IRegistrable {
    override fun register() {
        Stronghold.addThanksConsumer { owner, player ->
            if (owner != null) {
                owner.accept("Вас поблагодарили")
                owner.user.data.addMoney(100)
            }
            if (player != null) {
                player.accept("Вы поблагодарили")
                player.user.data.addMoney(100)
            }
        }

        Stronghold.onActivate {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.user.run {
                    data.speed.apply { applyBoosters(BoosterType.SPEED_BOOSTER) }
                    player?.walkSpeed = data.speed
                }
            }
        }

        Stronghold.onExpire {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.user.run {
                    data.speed.apply {
                        applyBoosters(BoosterType.SPEED_BOOSTER)
                    }
                    player?.walkSpeed = data.speed
                }
            }
        }
    }

    fun activateGlobal(user: User, time: Long, unit: TimeUnit, vararg boosters: BoosterType) {
        Stronghold.activateBoosters(
            *boosters.map { type ->
                BoosterGlobal.builder()
                    .type(type.label)
                    .title(type.title)
                    .owner(user.player)
                    .owner(user.player.cristalixName)
                    .duration(time, unit)
                    .multiplier(1.25)
                    .maxStackable(4)
                    .build()
            }.toTypedArray()
        )
    }
}
