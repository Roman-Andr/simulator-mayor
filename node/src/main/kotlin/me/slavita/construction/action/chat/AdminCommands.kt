package me.slavita.construction.action.chat

import me.func.mod.Anime
import me.func.mod.Kit
import me.func.mod.conversation.ModTransfer
import me.func.mod.reactive.ReactiveLine
import me.func.mod.reactive.ReactivePanel
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.menu.general.DailyMenu
import me.slavita.construction.city.bank.Bank
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.player.Data
import me.slavita.construction.player.Tags
import me.slavita.construction.prepare.GuidePrepare
import me.slavita.construction.prepare.TagsPrepare
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.killboard
import me.slavita.construction.utils.log
import me.slavita.construction.utils.opCommand
import me.slavita.construction.utils.scheduler
import me.slavita.construction.utils.user
import org.bukkit.Bukkit
import ru.cristalix.core.display.messages.RadioMessage
import ru.cristalix.core.realm.IRealmService
import ru.cristalix.core.transfer.ITransferService

object AdminCommands : IRegistrable {
    override fun register() {
        opCommand("setmoney") { player, args ->
            player.user.data.money = args[0].toLong()
        }

        opCommand("exp") { player, args ->
            player.user.data.addExp(args[0].toLong())
        }

        opCommand("sound") { player, args ->
            ModTransfer()
                .byteArray(*RadioMessage.serialize(RadioMessage(true, "")))
                .send("ilyafx:radio", player)
            ModTransfer()
                .byteArray(*RadioMessage.serialize(RadioMessage(true, args[0])))
                .send("ilyafx:radio", player)
            log(args[0])
        }

        opCommand("panel") { player, _ ->
            listOf(
                ReactivePanel.builder()
                    .text("Монет ${player.user.data.money.toMoneyIcon()}")
                    .color(GlowColor.ORANGE)
                    .progress(1.0)
                    .build(),
                ReactivePanel.builder()
                    .text("Опыт ${player.user.data.experience}")
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

        opCommand("dialog") { player, _ ->
            GuidePrepare.tryNext(player.user)
        }

        opCommand("scheduler") { player, _ ->
            player.accept("Число активных работников на сервере: ${scheduler.activeWorkers.size}")
            player.accept("Число задач: ${scheduler.pendingTasks.size}")
        }

        opCommand("kickAll") { _, _ ->
            // need test
            val availableRealms =
                IRealmService.get().typesAndRealms["SLVT"]!!.filter { it.realmId.id != IRealmService.get().currentRealmInfo.realmId.id }
            Bukkit.getOnlinePlayers().chunked(availableRealms.size).forEachIndexed { index, players ->
                players.forEach { player ->
                    // if (it.isOp) return@forEach
                    ITransferService.get().transfer(player.uniqueId, availableRealms[index].realmId)
                }
            }
        }

        opCommand("settag") { player, args ->
            player.user.data.tag = Tags.valueOf(args[0].uppercase())
            TagsPrepare.prepare(player.user)
        }

        opCommand("tags") { player, _ ->
            Tags.values().forEach { tag ->
                player.sendMessage(tag.tag)
            }
        }

        opCommand("construction:debug") { _, _ ->
            Anime.include(Kit.DEBUG)
        }

        opCommand("statclear") { player, _ ->
            player.user.data = Data(player.user)
        }

        opCommand("error") { player, _ ->
            val nu: String? = null
            player.killboard(nu!!)
        }

        opCommand("line") { player, _ ->
            ReactiveLine.builder()
                .to(player.user.currentCity.getSpawn())
                .build()
                .send(player)
        }
    }
}
