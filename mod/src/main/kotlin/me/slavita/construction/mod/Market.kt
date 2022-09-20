package me.slavita.construction.mod

import dev.xdark.clientapi.item.Item
import dev.xdark.clientapi.item.ItemStack
import ru.cristalix.clientapi.readUtf8
import ru.cristalix.uiengine.utility.Relative
import ru.cristalix.uiengine.utility.item
import ru.cristalix.uiengine.utility.rectangle

object Market {
    init {
        mod.registerChannel("market:stall") {
            val stall = rectangle {
                align = Relative.CENTER
                origin = Relative.CENTER
                enabled = true
                addChild(item {
                    align = Relative.TOP_RIGHT
                    origin = Relative.TOP_RIGHT
                    offset.x = 30.0
                    scale.x = 2.0
                    scale.y = 2.0
                })
            }
            val x = readInt()
            val y = readInt()
            val z = readInt()
            val id = readInt()
            val data = readByte()
            readUtf8().split(";").forEach {
                stall.addChild(item {
                    stack = ItemStack.of(Item.of(id), 1, data.toInt())
                })
            }
        }
    }
}