package me.slavita.construction.bank

import me.func.atlas.Atlas
import java.util.*

class Credit(
    val uuid: UUID,
    val creditValue: Long,
    val percent: Double,
) {
    val needToGive: Long
        get() = (creditValue * (1 + percent / 100) * Atlas.find("bank").getDouble("percent")).toLong()
}