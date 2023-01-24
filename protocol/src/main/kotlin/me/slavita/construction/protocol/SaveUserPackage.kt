package me.slavita.construction.protocol

import ru.cristalix.core.network.CorePackage

data class SaveUserPackage(
    val uuid: String,
    var data: String? = null,
    val experience: Long? = null,
    val projects: Long? = null,
) : CorePackage()