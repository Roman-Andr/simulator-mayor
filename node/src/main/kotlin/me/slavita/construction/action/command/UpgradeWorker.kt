package me.slavita.construction.action.command

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.app
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.worker.Worker
import org.bukkit.entity.Player

class UpgradeWorker(val user: Player, val worker: Worker) : CooldownCommand(user.player, 2) {
    override fun execute() {
        app.getUser(user).apply user@{
            workers.find { it == worker }!!.apply {
                this@user.tryPurchase(upgradePrice, {
                    levelUp()
                    player.playSound(MusicSound.LEVEL_UP)
                })
            }
        }
    }
}