package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.slavita.construction.player.City
import me.slavita.construction.player.User
import me.slavita.construction.utils.runAsync

object CitiesPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            currentCity = data.cities.firstOrNull { it.box.contains(player.location) }!!

            currentCity.playerCells.forEach {
                runAsync(200) {
                    it.updateStub()
                }
            }
        }
    }
}
