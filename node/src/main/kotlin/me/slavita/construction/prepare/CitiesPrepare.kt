package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.utils.runAsync

object CitiesPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            currentCity = data.cities.find { it.id == data.lastCityId }!!
            freelanceCell = PlayerCell(currentCity, app.mainWorld.freelanceCell, true)

            currentCity.playerCells.forEach {
                runAsync(100) {
                    it.updateStub()
                }
            }
        }
    }
}
