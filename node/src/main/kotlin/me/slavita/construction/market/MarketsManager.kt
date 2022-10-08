package me.slavita.construction.market

object MarketsManager {
    val markets = mutableListOf<Market>()

    init {
       markets.add(Market())
    }
}