package me.slavita.construction.action.command

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.utils.PlayerExtensions.accept
import me.slavita.construction.utils.user
import me.slavita.construction.worker.Worker
import org.bukkit.entity.Player

class UpgradeWorker(val user: Player, val worker: Worker) : CooldownCommand(user, 2) {
    override fun execute() {
        user.user.apply user@{
            workers.find { it == worker }!!.apply {
                this@user.tryPurchase(upgradePrice, {
                    levelUp()
                    player.accept("Вы успешно улучшили рабочего!")
                })
            }
        }
    }
}