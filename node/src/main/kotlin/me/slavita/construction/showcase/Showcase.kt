package me.slavita.construction.showcase

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.city.ShowcaseMenu
import me.slavita.construction.app
import me.slavita.construction.ui.Formatter.toTime
import me.slavita.construction.utils.toV3
import me.slavita.construction.utils.user
import kotlin.random.Random

class Showcase(val properties: ShowcaseProperties) {
    val box
        get() = app.mainWorld.map.getBox("showcase", properties.id.toString())
    val updateTime
        get() = (5 * 60 * 1000 - (System.currentTimeMillis() - lastUpdateTime)).toTime()
    private var lastUpdateTime = 0L

    fun init() {
        Anime.createReader("showcase:open") { player, buff ->
            if (buff.readInt() != properties.id) return@createReader

            player.user.showcaseMenu = ShowcaseMenu(
                player,
                this
            )
            player.user.showcaseMenu!!.tryExecute()
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