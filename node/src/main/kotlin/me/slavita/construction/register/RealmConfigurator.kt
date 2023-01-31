package me.slavita.construction.register

import me.func.mod.util.after
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.utils.toUUID
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.WHITE
import ru.cristalix.core.realm.IRealmService
import ru.cristalix.core.realm.RealmStatus
import ru.cristalix.core.scoreboard.IScoreboardService
import ru.cristalix.core.transfer.ITransferService

object RealmConfigurator : IRegistrable {
    override fun register() {
        IRealmService.get().currentRealmInfo.apply {
            readableName = "Тест"
            groupName = "Тест"
            status = RealmStatus.WAITING_FOR_PLAYERS
            maxPlayers = 250
            extraSlots = 15

            IScoreboardService.get().serverStatusBoard.displayName = "${WHITE}Тест #${AQUA}" + realmId.id
            after(20 * 4) {
                ITransferService.get().transfer(System.getenv("CONSTRUCTION_USER").toUUID(), realmId)
            }
        }
    }
}