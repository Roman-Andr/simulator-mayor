package me.slavita.construction.player.lootbox

import java.util.*

abstract class Lootbox(
    uuid: UUID = UUID.randomUUID()
) {
    abstract fun open()
}