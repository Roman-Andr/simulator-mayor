package me.slavita.construction.register

import me.func.mod.Anime
import me.func.protocol.math.Position
import me.slavita.construction.bank.Bank
import me.slavita.construction.common.utils.BANK_SUBMIT_CHANNEL
import me.slavita.construction.common.utils.FUNC_REWARD_CLICK
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.common.utils.STRUCTURE_PLACE_CHANNEL
import me.slavita.construction.region.ClientStructure
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.runTimerAsync
import me.slavita.construction.utils.user
import org.bukkit.Bukkit
import org.bukkit.ChatColor.DARK_GRAY
import org.bukkit.ChatColor.GOLD
import org.bukkit.ChatColor.WHITE
import ru.cristalix.core.realm.IRealmService
import kotlin.math.pow

object ModCallbacks : IRegistrable {
    override fun register() {
        Anime.createReader(BANK_SUBMIT_CHANNEL) { player, buff ->
            val amount = buff.readInt()
            val digit = buff.readInt()
            val value = (amount * 10.0.pow(digit)).toLong()
            player.accept("Кредит на сумму ${value.toMoneyIcon()} ${WHITE}успешно взят")
            Bank.giveCredit(player.user, value)
        }

        Anime.createReader(FUNC_REWARD_CLICK) { player, _ ->
            player.user.run {
                if (waitingReward == null) println("reward is null")
                waitingReward?.getReward(player.user)
                updateDaily()
            }
        }

        Anime.createReader(STRUCTURE_PLACE_CHANNEL) { player, _ ->
            player.user.data.cells.find { it.box.contains(player.location) }?.child?.run {
                if (this is ClientStructure) tryPlaceBlock()
            }
        }

        runTimerAsync(0, 5 * 20L) {
            Bukkit.getOnlinePlayers().forEach { player ->
                Anime.overlayText(
                    player,
                    Position.BOTTOM_RIGHT,
                    "Онлайн $DARK_GRAY» $GOLD" + IRealmService.get()
                        .getOnlineOnRealms("SLVT").toString()
                )
            }
        }
    }
}
