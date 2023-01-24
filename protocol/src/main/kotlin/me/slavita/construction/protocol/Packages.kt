package me.slavita.construction.protocol

import ru.cristalix.core.network.CorePackage

data class GetUserPackage(
    val uuid: String,
) : CorePackage() {
    var data: String? = null
}

data class SaveUserPackage(
    val uuid: String,
    var data: String? = null,
    val experience: Long? = null,
    val projects: Long? = null,
) : CorePackage()

class GetLeaderboardPackage : CorePackage() {
    var experience = arrayListOf<LeaderboardItem>()
    var projects = arrayListOf<LeaderboardItem>()
}
