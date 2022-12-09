package me.slavita.construction.action.chat

import dev.implario.bukkit.item.ItemBuilder
import me.func.atlas.Atlas
import me.func.mod.reactive.ReactivePanel
import me.func.mod.ui.menu.button
import me.func.mod.ui.menu.selection
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.action.command.menu.DailyMenu
import me.slavita.construction.bank.Bank
import me.slavita.construction.player.Tags
import me.slavita.construction.prepare.GuidePrepare
import me.slavita.construction.prepare.TagsPrepare
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.ChatCommandUtils.opCommand
import me.slavita.construction.utils.extensions.PlayerExtensions.killboard
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.utils.user
import org.bukkit.*
import ru.cristalix.core.formatting.Formatting.error
import ru.cristalix.core.formatting.Formatting.fine
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
                player.killboard(fine(Atlas.find(args[0]).get(args[1]).toString()))
            else {
                player.playSound(MusicSound.DENY)
                player.killboard(error("Конфиг или значение не найдены"))
            }
        }

        opCommand("refresh") { player, _ ->
            Atlas.update {
                player.killboard(fine("Конфигурация обновлена"))
            }
        }

        opCommand("dialog") { player, _ ->
            GuidePrepare.tryNext(player)
        }

        opCommand("scheduler") { player, _ ->
            player.killboard(fine("Число активных работников на сервере: ${Bukkit.server.scheduler.activeWorkers.size}"))
            player.killboard(fine("Число задач: ${Bukkit.server.scheduler.pendingTasks.size}"))
        }

        opCommand("kickAll") { _, _ ->
            //need test
            val availableRealms =
                IRealmService.get().typesAndRealms["SLVT"]!!.filter { it.realmId.id != IRealmService.get().currentRealmInfo.realmId.id }
            Bukkit.getOnlinePlayers().chunked(availableRealms.size).forEachIndexed { index, players ->
                players.forEach { player ->
                    //if (it.isOp) return@forEach
                    ITransferService.get().transfer(player.uniqueId, availableRealms[index].realmId)
                }
            }
        }

        opCommand("settag") { player, args ->
            player.user.tag = Tags.valueOf(args[0].uppercase())
            TagsPrepare.prepare(player.user)
        }

        opCommand("test") { player, _ ->
            selection {
                storage = mutableListOf(
                    *Material.values().map {
                        button {
                            item = if (it.isItem) ItemBuilder(it).build() else ItemBuilder(Material.BARRIER).build()
                        }
                    }.toTypedArray()
                )
            }.open(player)
        }

        opCommand("tags") { player, _ ->
            Tags.values().forEach { tag ->
                player.sendMessage(tag.tag)
            }
        }
    }
}