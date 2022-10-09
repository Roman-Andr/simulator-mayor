package me.slavita.construction.action.chat

import me.func.mod.reactive.ReactivePanel
import me.func.protocol.data.color.GlowColor
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.toMoneyIcon
import me.slavita.construction.utils.ChatCommandUtils.opCommand
import me.slavita.construction.utils.SpecialColor
import org.bukkit.Sound
import org.bukkit.SoundCategory

object AdminCommands {
    init {
        opCommand("money") { player, args ->
            app.getUser(player).stats.money += args[0].toLong()
        }

        opCommand("sound") { player, args ->
            player.playSound(player.location, Sound.valueOf(args[0]), SoundCategory.MASTER, 1.0f, 1.0f)
        }

        opCommand("panel") { player, args ->
            listOf(
                ReactivePanel.builder()
                    .text("Монет ${app.getUser(player).stats.money.toMoneyIcon()}")
                    .color(SpecialColor.GOLD)
                    .progress(1.0)
                    .build(),
                ReactivePanel.builder()
                    .text("Опыт ${app.getUser(player).stats.experience}")
                    .color(GlowColor.BLUE)
                    .progress(1.0)
                    .build(),
            ).forEach { it.send(player) }
        }
    }
}