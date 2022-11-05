package me.slavita.construction.mod.storage

import com.google.gson.Gson
import dev.xdark.clientapi.event.block.BlockRightClick
import dev.xdark.clientapi.util.EnumHand
import dev.xdark.feder.NetUtil
import io.netty.buffer.Unpooled
import me.slavita.construction.mod.mod
import me.slavita.construction.mod.player
import me.slavita.construction.mod.showcase.ShowcaseData
import me.slavita.construction.mod.utils.extensions.PositionExtensions.inBox
import ru.cristalix.uiengine.UIEngine

object Storage {
    var storages = arrayOf<StorageData>()

    init {
        mod.registerChannel("storage:initialize") {
            storages = Gson().fromJson(NetUtil.readUtf8(this), Array<StorageData>::class.java)
        }

        mod.registerHandler<BlockRightClick> {
            if (hand == EnumHand.OFF_HAND) return@registerHandler
            storages.forEach {
                if (!position.add(facing.xOffset, facing.yOffset, facing.zOffset).inBox(it.min, it.max)) return@forEach

                val buffer = Unpooled.buffer()
                UIEngine.clientApi.clientConnection().sendPayload("storage:open", buffer)

                player.swingArm(EnumHand.MAIN_HAND)
            }
        }
    }
}