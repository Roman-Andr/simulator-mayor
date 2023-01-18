package me.slavita.construction.dontate

import me.slavita.construction.player.Tags
import me.slavita.construction.player.User

open class TagDonate(
    title: String,
    description: String,
    price: Int,
    val tag: Tags,
) : Donate(title, description, price) {
    override fun purchaseSuccess(user: User) {
        user.data.ownTags.add(tag)
    }
}