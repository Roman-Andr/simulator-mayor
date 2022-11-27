package me.slavita.construction.dontate

import me.slavita.construction.booster.BoosterType
import me.slavita.construction.booster.Boosters
import me.slavita.construction.player.User
import java.util.concurrent.TimeUnit

open class BoosterPackDonate(
    title: String,
    description: String,
    price: Int,
    val time: Long,
    val unit: TimeUnit,
    vararg val boosters: BoosterType,
) : Donate(title, description, price) {
    override fun purchaseSuccess(user: User) {
        Boosters.activateGlobal(user, time, unit, *boosters)
    }
}