package me.slavita.construction.mod

import dev.xdark.clientapi.event.input.KeyPress
import dev.xdark.clientapi.gui.ingame.ChatScreen
import org.lwjgl.input.Keyboard
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.uiengine.UIEngine

object KeysManager {
    private val keys = mutableMapOf<Int, () -> Unit>()
    init {
        registerKey(Keyboard.KEY_M) {
            clientApi.chat().sendChatMessage("/menu")
        }
        registerKey(Keyboard.KEY_N) {
            clientApi.chat().sendChatMessage("/menu")
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