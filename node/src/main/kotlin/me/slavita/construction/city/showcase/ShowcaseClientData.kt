package me.slavita.construction.city.showcase

import ru.cristalix.core.math.V3

data class ShowcaseClientData(
    val id: Int,
    val title: String,
    val min: V3,
    val max: V3,
)
