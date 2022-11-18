package me.slavita.construction.multichat

import java.util.*

enum class ChatType(val title: String, val symbol: String, val key: String, val uuid: UUID) {
    SYSTEM(
        "Системный чат",
        "С",
        "system",
        UUID.randomUUID()
    )
}