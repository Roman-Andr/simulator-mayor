package me.slavita.construction.market.showcase

import ru.cristalix.core.math.V3
import java.util.*

data class ShowcaseDataForClient (
    val title: String,
    val min: V3,
    val max: V3,
    val uuid: UUID,
)