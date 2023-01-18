package me.slavita.construction.player

import me.func.atlas.Atlas
import me.slavita.construction.dontate.Abilities
import me.slavita.construction.showcase.Showcase
import me.slavita.construction.showcase.Showcases
import me.slavita.construction.worker.Worker

class Data(@Transient val user: User) {
    var statistics: Statistics = Statistics()
    val workers: HashSet<Worker> = hashSetOf()
    val settings: SettingsData = SettingsData()
    var tag: Tags = Tags.NONE
    val ownTags: HashSet<Tags> = hashSetOf(Tags.NONE)
    val abilities: HashSet<Abilities> = hashSetOf()
    val hall: CityHall = CityHall()
    val cities: HashSet<City> = Atlas.find("locations").getMapList("locations").map { values ->
        City(
            user,
            values["id"] as String,
            values["title"] as String,
            (values["price"] as Int).toLong(),
            values["id"] as String == "1"
        )
    }.toHashSet()
}
