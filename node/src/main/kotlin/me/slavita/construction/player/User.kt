package me.slavita.construction.player

import com.google.gson.GsonBuilder
import me.func.mod.Anime
import me.func.mod.conversation.ModTransfer
import me.slavita.construction.action.menu.city.ShowcaseMenu
import me.slavita.construction.showcase.Showcase
import me.slavita.construction.showcase.Showcases
import me.slavita.construction.common.utils.STORAGE_HIDE_CHANNEL
import me.slavita.construction.common.utils.STORAGE_SHOW_CHANNEL
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.dontate.AbilityDonate
import me.slavita.construction.dontate.Donates
import me.slavita.construction.listener.OnActions
import me.slavita.construction.prepare.StoragePrepare
import me.slavita.construction.region.StaticStructureState
import me.slavita.construction.reward.MoneyReward
import me.slavita.construction.reward.ReputationReward
import me.slavita.construction.reward.Reward
import me.slavita.construction.ui.HumanizableValues
import me.slavita.construction.ui.achievements.AchievementType
import me.slavita.construction.utils.accept
import me.slavita.construction.utils.deny
import me.slavita.construction.utils.runAsync
import me.slavita.construction.utils.user
import org.bukkit.Bukkit
import org.bukkit.ChatColor.GOLD
import ru.cristalix.core.invoice.IInvoiceService
import java.util.*
import kotlin.collections.HashSet
import kotlin.math.abs
import kotlin.random.Random

class User(val uuid: UUID) {
    val player = Bukkit.getPlayer(uuid)!!
    var income = 0L //todo: move to data (and remove lastIncome)
        set(value) {
            data.lastIncome = value
            field = value
        }
    val showcases: HashSet<Showcase> = Showcases.showcases.map { Showcase(it) }.toHashSet()
    private var criBalanceLastUpdate = 0L
    var inTrashZone = false
    var showcaseMenu: ShowcaseMenu? = null

    lateinit var data: Data

    var waitingReward: Reward? = null
    var showcaseMenuTaskId = 0 //todo: rewrite

    var criBalance: Int = 0
        get() {
            val now = System.currentTimeMillis()

            if (now - criBalanceLastUpdate > 1000 * 60) {
                criBalanceLastUpdate = now
                IInvoiceService.get().getBalanceData(uuid).thenAccept { data ->
                    field = data.crystals + data.coins
                }
            }
            return field
        }

    fun initialize(data: String?) {
        this.data = if (data == null) Data(this)
        else GsonBuilder()
            .create()
            .fromJson(data, Data::class.java)

        this.data.user = this

        //updateIncome()
    }

    fun tryPurchase(cost: Long, acceptAction: () -> Unit) {
        if (data.money >= cost) {
            data.money -= cost
            acceptAction()
        } else {
            Anime.close(player)
            player.deny("Недостаточно денег!")
        }
    }

    fun canPurchase(cost: Long) = data.money >= cost

    fun addAbility(ability: Abilities) {
        data.abilities.add(ability)
        ability.applyAction(this)
    }

    fun updateDaily() {
        val time = System.currentTimeMillis()
        data.nextDay++
        data.nextTakeDailyReward = time + 24 * 60 * 60 * 1000
    }

    fun receiveRandomReward() {
        data.run {
            when (Random.nextInt(100)) {
                in 0..5 ->
                    ReputationReward(
                        reputation / 100 +
                                Random.nextInt(1, 10) * level
                    ).getReward(this@User)

                in 0..20 ->
                    MoneyReward(money / 100).getReward(this@User)
            }
        }
    }
}
