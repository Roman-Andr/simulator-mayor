package me.slavita.construction.market

import java.util.*

object MarketsManager {
    val markets = mutableListOf<Market>()

    init {
        markets.add(Market())
    }
}