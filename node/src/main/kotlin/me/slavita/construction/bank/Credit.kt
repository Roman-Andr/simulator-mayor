package me.slavita.construction.bank

import me.func.atlas.Atlas
import me.slavita.construction.app
import java.util.*

class Credit(
    val uuid: UUID,
    val creditValue: Long,
    val percent: Double,
    val timeToGive: Long
) {
    var initialTime: Long = 0
    val timeSince: Long
        get() = (app.pass - initialTime) / 20
    val timeLast: Long
        get() = if (timeToGive -  timeSince > 0) timeToGive -  timeSince else 0
    val needToGive: Long
        get() = (creditValue * (1 + percent / 100) * (if (timeLast == 0L) Atlas.find("bank").getDouble("percent") else 1.0)).toLong()

    init {
        initialTime = app.pass
    }
}