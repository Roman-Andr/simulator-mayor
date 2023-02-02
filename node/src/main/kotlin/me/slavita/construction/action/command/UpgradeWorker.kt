package me.slavita.construction.action.command

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.User
import me.slavita.construction.utils.accept
import me.slavita.construction.worker.Worker

class UpgradeWorker(override val user: User, val worker: Worker) : CooldownCommand(user, 2) {
    override fun execute() {
        user.apply user@{
            data.workers.find { it == worker }!!.apply {
                this@user.tryPurchase(upgradePrice) {
                    levelUp()
                    player.accept("Вы успешно улучшили рабочего!")
                }
            }
        }
    }
}
