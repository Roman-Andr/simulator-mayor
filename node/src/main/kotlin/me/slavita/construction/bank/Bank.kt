package me.slavita.construction.bank

import me.slavita.construction.player.User
import me.slavita.construction.reward.MoneyReward
import java.util.UUID

object Bank {
    val playersData = mutableMapOf<UUID, HashSet<Credit>>()

    fun register(user: User) {
        playersData[user.player.uniqueId] = hashSetOf()
    }

    fun giveCredit(user: User, amount: Long) {
        playersData[user.player.uniqueId]!!.add(
            Credit(
                UUID.randomUUID(),
                amount,
                3.0
            )
        )
        MoneyReward(amount, false).getReward(user)
    }

    fun repayCredit(user: User, uuid: UUID, acceptAction: () -> Unit) {
        val credit = playersData[user.player.uniqueId]!!.find { it.uuid == uuid }
        user.tryPurchase(credit!!.needToGive) {
            playersData[user.player.uniqueId]!!.remove(credit)
            acceptAction()
        }
    }
}
