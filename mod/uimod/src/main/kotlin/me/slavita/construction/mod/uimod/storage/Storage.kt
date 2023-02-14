package me.slavita.construction.mod.uimod.storage

import com.google.gson.Gson
import dev.xdark.clientapi.event.block.BlockRightClick
import dev.xdark.clientapi.util.EnumHand
import dev.xdark.feder.NetUtil
import io.netty.buffer.Unpooled
import me.slavita.construction.common.utils.IRegistrable
import me.slavita.construction.common.utils.STORAGE_HIDE_CHANNEL
import me.slavita.construction.common.utils.STORAGE_INIT_CHANNEL
import me.slavita.construction.common.utils.STORAGE_SHOW_CHANNEL
import me.slavita.construction.mod.uimod.mod
import me.slavita.construction.mod.uimod.player
import me.slavita.construction.mod.uimod.templates.BoxData
import me.slavita.construction.mod.uimod.templates.info
import me.slavita.construction.mod.uimod.templates.infoZone
import me.slavita.construction.mod.uimod.utils.inBox
import me.slavita.construction.mod.uimod.utils.sendPayload
import ru.cristalix.uiengine.UIEngine

object Storage : IRegistrable {
    var storages = arrayOf<StorageData>()

    private val infoZone = infoZone {
        info.description("Открыть склад")
    }

    private val enterInfo = info {
        title("Склад")
        description("Положить на склад - выкинуть [Q]")
        offset.y += 40.0
    }

    override fun register() {
        UIEngine.overlayContext.addChild(infoZone.info)
        UIEngine.overlayContext.addChild(enterInfo)

        mod.registerChannel(STORAGE_INIT_CHANNEL) {
            storages = Gson().fromJson(NetUtil.readUtf8(this), Array<StorageData>::class.java)
            infoZone.info.boxes = storages.map { BoxData(it.title, it.min, it.max) }.toTypedArray()
        }

        mod.registerChannel(STORAGE_SHOW_CHANNEL) {
            enterInfo.show()
        }

        mod.registerChannel(STORAGE_HIDE_CHANNEL) {
            enterInfo.hide()
        }

        mod.registerHandler<BlockRightClick> {
            if (hand == EnumHand.OFF_HAND) return@registerHandler
            storages.forEach { storage ->
                if (!position.add(facing.xOffset, facing.yOffset, facing.zOffset)
                    .inBox(storage.min, storage.max)
                ) return@forEach

                val buffer = Unpooled.buffer()
                sendPayload("storage:open", buffer)

                player.swingArm(EnumHand.MAIN_HAND)
            }
        }
    }
}
