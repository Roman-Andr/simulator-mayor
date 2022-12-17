package me.slavita.construction.mod.storage

import com.google.gson.Gson
import dev.xdark.clientapi.event.block.BlockRightClick
import dev.xdark.clientapi.util.EnumHand
import dev.xdark.feder.NetUtil
import io.netty.buffer.Unpooled
import me.slavita.construction.mod.mod
import me.slavita.construction.mod.player
import me.slavita.construction.mod.templates.BoxData
import me.slavita.construction.mod.templates.info
import me.slavita.construction.mod.templates.infoZone
import me.slavita.construction.mod.utils.extensions.PositionExtensions.inBox
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.UIEngine.clientApi

object Storage {
    var storages = arrayOf<StorageData>()

    private val infoZone = infoZone {
        info.description("Открыть хранилище")
    }

    private val enterInfo = info {
        title("Хранилище")
        description("Положить на склад - выкинуть [Q]")
        offset.y += 40.0
    }

    init {
        UIEngine.overlayContext.addChild(infoZone.info)
        UIEngine.overlayContext.addChild(enterInfo)

        mod.registerChannel("storage:initialize") {
            storages = Gson().fromJson(NetUtil.readUtf8(this), Array<StorageData>::class.java)
            infoZone.info.boxes = storages.map { BoxData(it.title, it.min, it.max) }.toTypedArray()
        }

        mod.registerChannel("storage:show") {
            enterInfo.show()
        }

        mod.registerChannel("storage:hide") {
            enterInfo.hide()
        }

        mod.registerHandler<BlockRightClick> {
            if (hand == EnumHand.OFF_HAND) return@registerHandler
            storages.forEach { storage ->
                if (!position.add(facing.xOffset, facing.yOffset, facing.zOffset)
                        .inBox(storage.min, storage.max)) return@forEach

                val buffer = Unpooled.buffer()
                clientApi.clientConnection().sendPayload("storage:open", buffer)

                player.swingArm(EnumHand.MAIN_HAND)
            }
        }
    }
}