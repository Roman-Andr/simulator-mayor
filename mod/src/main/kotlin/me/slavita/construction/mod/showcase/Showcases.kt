package me.slavita.construction.mod.showcase

import com.google.gson.Gson
import dev.xdark.clientapi.event.block.BlockLeftClick
import dev.xdark.clientapi.event.block.BlockRightClick
import dev.xdark.clientapi.event.lifecycle.GameLoop
import dev.xdark.clientapi.math.BlockPos
import dev.xdark.clientapi.util.EnumFacing
import dev.xdark.clientapi.util.EnumHand
import dev.xdark.feder.NetUtil
import io.netty.buffer.Unpooled
import me.slavita.construction.mod.mod
import me.slavita.construction.mod.player
import me.slavita.construction.mod.utils.extensions.PositionExtensions.inBox
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.UIEngine.clientApi
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.utility.*

object Showcases {
    private lateinit var showcaseText: TextElement
    private var showcaseBox = carved {
        size = V3(210.0, 40.0)
        align = BOTTOM
        origin = BOTTOM
        offset.y -= 65.0
        color = Color(0, 0, 0, 0.52)
        enabled = false
        showcaseText = +text {
            origin = TOP
            align = TOP
            offset.y += 4.0
            color = Color(0, 255, 0, 1.0)
        }
        +text {
            origin = BOTTOM
            align = BOTTOM
            offset.y -= 4.0
            color = Color(255, 255, 255, 1.0)
            content = "Открыть витрину [ПКМ]"
        }
    }

    init {
        var showcases: Array<ShowcaseData>? = null

        UIEngine.overlayContext.addChild(showcaseBox)

        mod.registerHandler<GameLoop> {
            clientApi.minecraft().mouseOver.pos?.run {
                var shown = false

                showcases?.forEach {
                    if (inBox(it.min, it.max)) {
                        showcaseText.content = it.title
                        showcaseBox.enabled = true
                        shown = true
                    }
                }

                if (!shown && showcaseBox.enabled) {
                    showcaseBox.enabled = false
                }
            }
        }

        mod.registerHandler<BlockRightClick> {
            if (hand == EnumHand.OFF_HAND) return@registerHandler
            onClick(showcases, position, facing)
        }

        mod.registerHandler<BlockLeftClick> {
            onClick(showcases, position, facing)
        }

        mod.registerChannel("showcase:initialize") {
            showcases = Gson().fromJson(NetUtil.readUtf8(this), Array<ShowcaseData>::class.java)
        }
    }

    fun onClick(showcases: Array<ShowcaseData>?, position: BlockPos, facing: EnumFacing) {
        showcases?.forEach {
            if (!position.add(facing.xOffset, facing.yOffset, facing.zOffset).inBox(it.min, it.max)) return@forEach

            val buffer = Unpooled.buffer().writeInt(it.id)
            clientApi.clientConnection().sendPayload("showcase:open", buffer)

            player.swingArm(EnumHand.MAIN_HAND)
        }
    }
}