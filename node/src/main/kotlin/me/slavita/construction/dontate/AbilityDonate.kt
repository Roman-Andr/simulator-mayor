package me.slavita.construction.dontate

import me.slavita.construction.player.User

open class AbilityDonate(title: String, description: String, price: Int, val ability: Abilities) :
    Donate(title, description, price) {
    override fun purchaseSuccess(user: User) {
        user.addAbility(ability)
    }
}
