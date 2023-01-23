package me.slavita.construction.showcase

import me.slavita.construction.common.utils.TimeFormatter
import me.slavita.construction.world.ItemProperties

class ShowcaseProperties(val id: Int, val name: String, var elements: HashSet<ShowcaseProduct>) {
    val updateTime
        get() = TimeFormatter.toTimeFormat(10000 - (System.currentTimeMillis() - lastUpdateTime))
    private var lastUpdateTime = 0L

    fun updatePrices() {
        lastUpdateTime = System.currentTimeMillis()
        elements.forEach {
            it.price = (100L * Random.nextDouble(0.7, 1.4)).toLong()
        }
    }
}
