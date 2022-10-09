package me.slavita.construction.action.chat

import me.slavita.construction.app
import me.slavita.construction.utils.ChatCommandUtils.opCommand
import org.bukkit.Sound
import org.bukkit.SoundCategory

object AdminCommands {
    init {
        opCommand("money") { player, args ->
            app.getUser(player).stats.money += args[0].toInt()
        }

        opCommand("sound") { player, args ->
            player.playSound(player.location, Sound.valueOf(args[0]), SoundCategory.MASTER, 1.0f, 1.0f)
        }
    }
}