package me.slavita.construction.protocol

import ru.cristalix.core.network.CorePackage

data class SaveUserPackage(
    val uuid: String,
    var data: String,
    val experience: Long,
    val projects: Long,
    val totalBoosters: Long,
    val lastIncome: Long,
    val money: Long,
    val reputation: Long,
) : CorePackage()
