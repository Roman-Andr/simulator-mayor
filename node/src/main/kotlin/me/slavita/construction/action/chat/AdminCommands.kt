package me.slavita.construction.action.chat

import Nightingale
import me.func.atlas.Atlas
import me.func.mod.reactive.ReactivePanel
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.func.protocol.data.emoji.Emoji
import me.slavita.construction.action.command.menu.DailyMenu
import me.slavita.construction.bank.Bank
import me.slavita.construction.prepare.GuidePrepare
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.ChatCommandUtils.opCommand
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.user
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.SoundCategory
import ru.cristalix.core.realm.IRealmService
import ru.cristalix.core.transfer.ITransferService

object AdminCommands {
    init {
        opCommand("setmoney") { player, args ->
            player.user.statistics.money = args[0].toLong()
        }

        opCommand("exp") { player, args ->
            player.user.addExp(args[0].toLong())
        }

        opCommand("sound") { player, args ->
            player.playSound(player.location, Sound.valueOf(args[0]), SoundCategory.MASTER, 1.0f, 1.0f)
        }

        opCommand("panel") { player, _ ->
            listOf(
                ReactivePanel.builder()
                    .text("Монет ${player.user.statistics.money.toMoneyIcon()}")
                    .color(GlowColor.ORANGE)
                    .progress(1.0)
                    .build(),
                ReactivePanel.builder()
                    .text("Опыт ${player.user.statistics.experience}")
                    .color(GlowColor.BLUE)
                    .progress(1.0)
                    .build(),
            ).forEach { it.send(player) }
        }

        opCommand("daily") { player, _ ->
            DailyMenu(player).tryExecute()
        }

        opCommand("credit") { player, args ->
            Bank.giveCredit(player.user, args[0].toLong())
        }

        opCommand("config") { player, args ->
            if (Atlas.find(args[0]).get(args[1]) != null)
                player.killboard(Atlas.find(args[0]).get(args[1]).toString())
            else
                player.killboard("Конфиг или значение не найдены")
        }

        opCommand("refresh") { player, _ ->
            Atlas.update {
                player.killboard("Конфигурация обновлена")
            }
        }

        opCommand("bc") { _, _ ->
            Nightingale.broadcast("construction", "Всем привет!")
        }

        opCommand("dialog") { player, _ ->
            GuidePrepare.tryNext(player)
        }

        opCommand("kickAll") { _, _ ->
            //need test
            println("here")
            val availableRealms =
                IRealmService.get().typesAndRealms["SLVT"]!!.filter { it.realmId.id != IRealmService.get().currentRealmInfo.realmId.id }
            Bukkit.getOnlinePlayers().chunked(availableRealms.size).forEachIndexed { index, players ->
                players.forEach {
                    //if (it.isOp) return@forEach
                    ITransferService.get().transfer(it.uniqueId, availableRealms[index].realmId)
                }
            }
        }


    }
}