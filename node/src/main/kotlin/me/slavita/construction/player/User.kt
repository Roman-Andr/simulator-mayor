package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.booster.BoosterType
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.prepare.StoragePrepare
import me.slavita.construction.project.Project
import me.slavita.construction.storage.BlocksStorage
import me.slavita.construction.ui.Formatter.applyBoosters
import me.slavita.construction.utils.PlayerExtensions.deny
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.cristalix.core.invoice.IInvoiceService
import java.util.*

class User(val uuid: UUID) {
    var initialized = false
    lateinit var player: Player
    lateinit var data: Data
    val statistics
        get() = data.statistics
    var cities = hashSetOf<City>()
    var currentCity: City = City(this, "1", "Незаданная", 0, true)
    val blocksStorage = BlocksStorage(this)
    var watchableProject: Project? = null
    var income = 0L
    var criBalanceLastUpdate = 0L
    val hall = CityHall(this)

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
            if (initialized && player.isOnline) statistics.money += income.applyBoosters(BoosterType.MONEY_BOOSTER)
        }, 0L, 20L)
    }

    fun tryPurchase(
        cost: Long,
        acceptAction: () -> Unit,
    ) {
        if (statistics.money >= cost) {
            statistics.money -= cost
            acceptAction()
        } else {
            player.deny("Недостаточно денег!")
        }
    }

    fun addExp(exp: Long) {
        statistics.experience += exp
//		if (exp / 10*2.0.pow(stats.level) > 0) {
//			stats.level += (exp / 10).toInt()
//			Anime.itemTitle(player, ItemIcons.get("other", "access"), "Новый уровень: ${stats.level}", "", 2.0)
//			Glow.animate(player, 2.0, GlowColor.GREEN)
//		}
    }

    fun canPurchase(cost: Long): Boolean {
        return statistics.money >= cost
    }

    fun changeCity(city: City) {
        currentCity.projects.forEach { it.structure.deleteVisual() }

        player.teleport(city.getSpawn())
        currentCity = city

        StoragePrepare.prepare(this)

        city.projects.forEach { it.structure.visual.start() }
    }

    fun addAbility(ability: Abilities) {
        data.abilities.add(ability)
        ability.applyAction(this)
    }
}
