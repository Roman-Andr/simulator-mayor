package me.slavita.construction.action.command

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.user
import me.slavita.construction.worker.Worker
import org.bukkit.entity.Player

class UpgradeWorker(player: Player, val worker: Worker) : CooldownCommand(player, 2) {
    override fun execute() {
        player.user.apply user@{
            data.workers.find { it == worker }!!.apply {
                this@user.tryPurchase(upgradePrice) {
                    levelUp()
                    player.accept("Вы успешно улучшили рабочего!")
                }
            }
        }
    }
}