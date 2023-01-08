package me.slavita.construction.prepare

import me.func.atlas.Atlas
import me.slavita.construction.app
import me.slavita.construction.player.City
import me.slavita.construction.player.User
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.utils.runAsync

object CitiesPrepare : IPrepare {
    override fun prepare(user: User) {
        user.apply {
            if (cities.isEmpty()) {
                cities.addAll(Atlas.find("locations").getMapList("locations").map { values ->
                    City(
                        this,
                        values["id"] as String,
                        values["title"] as String,
                        (values["price"] as Int).toLong(),
                        values["id"] as String == "1"
                    )
                }.toTypedArray())
            }

            currentCity = cities.firstOrNull { it.box.contains(player.location) }!!
            freelanceCell = PlayerCell(currentCity, app.mainWorld.freelanceCell, true)

            currentCity.playerCells.forEach {
                runAsync(30) {
                    it.updateStub()
                }
            }
        }
    }
}
