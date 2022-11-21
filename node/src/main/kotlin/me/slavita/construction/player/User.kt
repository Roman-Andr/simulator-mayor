package me.slavita.construction.player

import me.func.mod.util.after
import me.slavita.construction.app
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.player.guide.Guide
import me.slavita.construction.project.Project
import me.slavita.construction.storage.BlocksStorage
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.utils.music.MusicExtension.playSound
import me.slavita.construction.utils.music.MusicSound
import me.slavita.construction.worker.Worker
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.cristalix.core.invoice.IInvoiceService
import java.util.*

class User(
    val uuid: UUID,
    var stats: Statistics,
) {
    val guide = Guide()
    var player: Player = Bukkit.getPlayer(uuid)
    val cites = arrayOf(City(this, "1"), City(this, "2"))
    var currentCity = cites.firstOrNull { return@firstOrNull it.box.contains(player.location) } ?: cites[0]
    val blocksStorage = BlocksStorage(this)
    var workers = hashSetOf<Worker>()
    var watchableProject: Project? = null
    var income = 0L
    var dialogId = 0
    var criBalanceLastUpdate = 0L
    var criBalance: Int = 0
        get() {
            val now = System.currentTimeMillis()

            if (now - criBalanceLastUpdate > 1000 * 60) {
                criBalanceLastUpdate = now
                IInvoiceService.get().getBalanceData(player.uniqueId).thenAccept { data ->
                    field = data.crystals + data.coins
                }
            }
            return field
        }

    init {
        Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
            stats.money += income
        }, 0L, 20L)
    }

    fun tryPurchase(
        cost: Long,
        acceptAction: () -> Unit,
        denyAction: () -> Unit = { player.playSound(MusicSound.DENY) },
    ) {
        if (stats.money >= cost) {
            stats.money -= cost
            acceptAction()
        } else {
            denyAction()
        }
    }

    fun addExp(exp: Long) {
        stats.experience += exp
//		if (exp / 10*2.0.pow(stats.level) > 0) {
//			stats.level += (exp / 10).toInt()
//			Anime.itemTitle(player, ItemIcons.get("other", "access"), "Новый уровень: ${stats.level}", "", 2.0)
//			Glow.animate(player, 2.0, GlowColor.GREEN)
//		}
    }

    fun canPurchase(cost: Long): Boolean {
        return stats.money >= cost
    }
}