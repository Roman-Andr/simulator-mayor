package me.slavita.construction.mod.showcase

import com.google.gson.Gson
import dev.xdark.clientapi.event.block.BlockLeftClick
import dev.xdark.clientapi.event.block.BlockRightClick
import dev.xdark.clientapi.math.BlockPos
import dev.xdark.clientapi.util.EnumFacing
import dev.xdark.clientapi.util.EnumHand
import dev.xdark.feder.NetUtil
import io.netty.buffer.Unpooled
import me.slavita.construction.mod.mod
import me.slavita.construction.mod.player
import me.slavita.construction.mod.templates.BoxData
import me.slavita.construction.mod.templates.infoZone
import me.slavita.construction.mod.utils.extensions.PositionExtensions.inBox
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.utility.*

object Showcases {

    private var showcases = arrayOf<ShowcaseData>()

    private val infoZone = infoZone {
        info.description("Открыть витрину")
    }

    init {
        UIEngine.overlayContext.addChild(infoZone.info)

        mod.registerHandler<BlockRightClick> {
            if (hand == EnumHand.OFF_HAND) return@registerHandler
            onClick(showcases, position, facing)
        }

        mod.registerHandler<BlockLeftClick> {
            onClick(showcases, position, facing)
        }

        mod.registerChannel("showcase:initialize") {
            showcases = Gson().fromJson(NetUtil.readUtf8(this), Array<ShowcaseData>::class.java)
            infoZone.info.boxes = showcases.map { BoxData(it.title, it.min, it.max) }.toTypedArray()
        }
    }

    private fun onClick(showcases: Array<ShowcaseData>?, position: BlockPos, facing: EnumFacing) {
        showcases?.forEach { data ->
            if (!position.add(facing.xOffset, facing.yOffset, facing.zOffset).inBox(data.min, data.max)) return@forEach

            val buffer = Unpooled.buffer().writeInt(data.id)
            clientApi.clientConnection().sendPayload("showcase:open", buffer)

            player.swingArm(EnumHand.MAIN_HAND)
        }
    }
}