package me.slavita.construction.prepare

import me.slavita.construction.city.bank.Bank
import me.slavita.construction.player.User

object BankAccountPrepare : IPrepare {
    override fun prepare(user: User) {
        Bank.register(user)
    }
}