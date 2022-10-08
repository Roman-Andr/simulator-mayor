package me.slavita.construction.mod

import dev.xdark.clientapi.event.input.KeyPress
import dev.xdark.clientapi.gui.ingame.ChatScreen
import io.netty.buffer.Unpooled
import org.lwjgl.input.Keyboard
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.uiengine.UIEngine

object KeysManager {
    private val keys = mutableMapOf<Int, () -> Unit>()
    init {
        registerKey(Keyboard.KEY_M) {
            clientApi.clientConnection().sendPayload("menu:open", Unpooled.EMPTY_BUFFER)
        }
        registerKey(Keyboard.KEY_N) {
            clientApi.clientConnection().sendPayload("menu:open", Unpooled.EMPTY_BUFFER)
        }

        mod.registerHandler<KeyPress> {
            if (UIEngine.clientApi.minecraft().currentScreen() is ChatScreen)
                return@registerHandler
            keys[key]?.invoke()
        }
    }

    private fun registerKey(key: Int, action: () -> Unit) {
        keys[key] = action
    }
}