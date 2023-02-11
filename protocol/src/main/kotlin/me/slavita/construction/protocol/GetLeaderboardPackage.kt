package me.slavita.construction.protocol

import ru.cristalix.core.network.CorePackage

data class GetLeaderboardPackage(
    val field: String,
) : CorePackage() {
    var top: ArrayList<LeaderboardItem> = arrayListOf()
}
