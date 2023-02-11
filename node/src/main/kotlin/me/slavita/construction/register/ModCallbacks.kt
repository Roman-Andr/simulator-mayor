package me.slavita.construction.register

import me.func.mod.Anime
import me.func.protocol.math.Position
import me.slavita.construction.city.bank.Bank
import me.slavita.construction.common.utils.BANK_SUBMIT_CHANNEL
import me.slavita.construction.common.utils.FUNC_REWARD_CLICK
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.common.utils.STRUCTURE_PLACE_CHANNEL
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.structure.ClientStructure
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
//        Anime.createReader("menu:open") { player, _ ->
//            if (player.user.watchableProject != null) {
//                BuildingControlMenu(player, player.user.watchableProject!!).tryExecute()
//            }
//        }

        Anime.createReader(BANK_SUBMIT_CHANNEL) { player, buff ->
            val amount = buff.readInt()
            val digit = buff.readInt()
            val value = (amount * 10.0.pow(digit)).toLong()
            player.accept("Кредит на сумму ${value.toMoneyIcon()} ${WHITE}успешно взят")
            Bank.giveCredit(player.user, value)
        }

        Anime.createReader(FUNC_REWARD_CLICK) { player, _ ->
            MoneyReward(100).getReward(player.user)
            player.user.updateDaily()
        }

        Anime.createReader(STRUCTURE_PLACE_CHANNEL) { player, _ ->
            Bukkit.getOnlinePlayers().forEach { owner ->
                if (player == owner) (owner.user.watchableProject!!.structure as ClientStructure).tryPlaceBlock()
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
