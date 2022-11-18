package me.slavita.construction.market.showcase

import ru.cristalix.core.math.V3

data class ShowcaseClientData(
    val title: String,
    val min: V3,
    val max: V3,
    val id: Int,
)