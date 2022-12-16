package me.slavita.construction.bank

import me.slavita.construction.player.User
import java.util.*

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
        user.data.statistics.money += amount
    }

    fun repayCredit(user: User, uuid: UUID, acceptAction: () -> Unit) {
        val credit = playersData[user.player.uniqueId]!!.find { it.uuid == uuid }
        user.tryPurchase(credit!!.needToGive) {
            playersData[user.player.uniqueId]!!.remove(credit)
            acceptAction()
        }
    }
}