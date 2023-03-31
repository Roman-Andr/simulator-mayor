package me.slavita.construction.bank

import java.util.UUID

class Credit(
    val uuid: UUID,
    val creditValue: Long,
    val percent: Double,
) {
    val needToGive: Long
        get() = (creditValue * (1 + percent / 100)).toLong()
}
