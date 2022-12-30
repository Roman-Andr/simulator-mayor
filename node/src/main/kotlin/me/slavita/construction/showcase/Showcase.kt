package me.slavita.construction.showcase

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.ShowcaseMenu
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.toTime
import me.slavita.construction.utils.BlocksExtensions.toV3
import kotlin.random.Random

class Showcase(val properties: ShowcaseProperties) {
    val box
        get() = app.mainWorld.map.getBox("showcase", properties.id.toString())
    val updateTime
        get() = (10000 - (System.currentTimeMillis() - lastUpdateTime)).toTime()
    private var lastUpdateTime = 0L

    fun init() {
        Anime.createReader("showcase:open") { player, buff ->
            if (buff.readInt() != properties.id) return@createReader

            ShowcaseMenu(
                player,
                this
            ).tryExecute()
        }
    }

    fun getData(): ShowcaseClientData {
        return ShowcaseClientData(properties.id, properties.name, box.min.toV3(), box.max.toV3())
    }

    fun updatePrices() {
        lastUpdateTime = System.currentTimeMillis()
        properties.elements.forEach {
            it.price = (100L * Random.nextDouble(0.7, 1.4)).toLong()
        }
    }
}