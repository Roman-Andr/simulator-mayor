package me.slavita.construction.market

class Market {
    var instances: List<Showcase>? = null

    init {
        instances = Showcases.showcases.map { Showcase(it) }
    }
}