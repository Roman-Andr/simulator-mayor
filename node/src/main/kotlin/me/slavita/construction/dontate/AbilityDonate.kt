package me.slavita.construction.dontate

import me.slavita.construction.dontate.ability.Ability
import me.slavita.construction.player.User

open class AbilityDonate(title: String, description: String, price: Int, vararg val abilities: Ability) :
    Donate(title, description, price) {
    override fun purchaseSuccess(user: User) {
        abilities.forEach { user.addAbility(it) }
    }
}