package me.slavita.construction.protocol

import ru.cristalix.core.network.CorePackage

data class GetUserPackage(
    val uuid: String,
) : CorePackage() {
    var data: String? = null
}

data class SaveUserPackage(
    val uuid: String,
    val data: String,
) : CorePackage()
