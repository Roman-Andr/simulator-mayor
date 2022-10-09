package me.slavita.construction.market

import me.slavita.construction.market.showcase.Showcase
import me.slavita.construction.market.showcase.Showcases

class Market {
    var instances: List<Showcase>? = null

    init {
        instances = Showcases.showcases.map { Showcase(it) }
    }
}