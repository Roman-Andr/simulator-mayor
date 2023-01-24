package me.slavita.construction.protocol

import ru.cristalix.core.network.CorePackage

data class GetLeaderboardPackage(
    var experience: ArrayList<LeaderboardItem> = arrayListOf(),
    var projects: ArrayList<LeaderboardItem> = arrayListOf()
) : CorePackage()