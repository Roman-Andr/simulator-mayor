package me.slavita.construction.region

import me.slavita.construction.app

class RegionOptions(val index: Int, val name: String) {

    val box = app.mainWorld.map.box("region", "$index")
    val spawn = app.mainWorld.map.labels("region-spawn", "$index")[0]
}
