package me.slavita.construction.prepare

import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.structure.CityCell
import me.slavita.construction.utils.runAsync

object CitiesPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            currentCity = data.cities.find { it.id == data.lastCityId }!!
            freelanceCell = CityCell(
                currentCity,
                app.mainWorld.freelanceCell,
                true
            )

            currentCity.cityCells.forEach {
                runAsync(5 * 20) {
                    it.updateStub()
                }
            }
        }
    }
}
