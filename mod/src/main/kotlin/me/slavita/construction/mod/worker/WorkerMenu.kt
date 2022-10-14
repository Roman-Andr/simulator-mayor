package me.slavita.construction.mod.worker

import dev.xdark.clientapi.event.input.KeyPress
import io.netty.buffer.Unpooled
import me.slavita.construction.mod.KeysManager
import me.slavita.construction.mod.mod
import org.lwjgl.input.Keyboard
import ru.cristalix.clientapi.JavaMod.clientApi
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.element.ContextGui
import ru.cristalix.uiengine.eventloop.animate
import ru.cristalix.uiengine.onMouseUp
import ru.cristalix.uiengine.utility.*

object WorkerMenu {

    private var context = ContextGui()
    private var opened = false

    init {

        KeysManager.registerKey(Keyboard.KEY_V) {
            println("АБОБА")
            ContextGui().apply {
                addChild(carved {
                    size = UIEngine.overlayContext.size
                    color = Color(0, 0, 0, 0.8)
                    align = CENTER
                    origin = CENTER

                    +text {
                        content = "Профиль рабочего"
                        color = WHITE
                        align = V3(0.5, 0.3)
                        origin = CENTER
                        scale = V3(2.0, 2.0)
                        shadow = true
                    }

                    +rectangle {
                        align = V3(0.45, 0.5)
                        origin = CENTER
                        color = Color(105, 105, 105,0.6)
                        +text {
                            content = "Информация: " +
                                    "\nИмя Руслан" +
                                    "\nФамилия Руслан" +
                                    "\nИмя Руслан" +
                                    "\nФамилия Руслан" +
                                    "\nИмя Руслан" +
                                    "\nФамилия Руслан"
                            color = WHITE
                            align = CENTER
                            origin = CENTER
                            scale = V3(1.3, 1.3)
                            shadow = true
                        }
                    }

                    +rectangle {
                        align = V3(0.6, 0.5)
                        origin = CENTER
                        size = V3(170.0, 150.0)

                        +carved {
                            carveSize = 2.0
                            align = TOP
                            origin = TOP
                            val text = "Улучшить"
                            val normalColor = Color(68, 255, 81, 0.83)
                            val hoveredColor = Color(85 , 255, 125, 0.83)
                            size = V3(70.0, 64.0)
                            color = normalColor
                            +text {
                                content = text
                                color = WHITE
                                align = CENTER
                                origin = CENTER
                                scale = V3(0.9, 0.9)
                                shadow = true
                            }

                            onHover {
                                animate(0.08, Easings.QUINT_OUT) {
                                    color = if (hovered) hoveredColor else normalColor
                                    scale = V3(if (hovered) 1.1 else 1.0, if (hovered) 1.1 else 1.0, 1.0)
                                }
                            }
                        }

                        +carved {
                            carveSize = 2.0
                            align = BOTTOM
                            origin = BOTTOM
                            val text = "Продать"
                            val normalColor = Color(160, 29, 40, 0.83)
                            val hoveredColor = Color(231, 61, 75, 0.83)
                            size = V3(70.0, 64.0)
                            color = normalColor
                            +text {
                                content = text
                                color = WHITE
                                align = CENTER
                                origin = CENTER
                                scale = V3(0.9, 0.9)
                                shadow = true
                            }

                            onHover {
                                animate(0.08, Easings.QUINT_OUT) {
                                    color = if (hovered) hoveredColor else normalColor
                                    scale = V3(if (hovered) 1.1 else 1.0, if (hovered) 1.1 else 1.0, 1.0)
                                }
                            }
                        }
                    }

                    +carved {
                        carveSize = 2.0
                        align = V3(0.5, 0.7)
                        origin = CENTER
                        size = V3(76.0, 20.0)
                        val normalColor = Color(160, 29, 40, 0.83)
                        val hoveredColor = Color(231, 61, 75, 0.83)
                        color = normalColor
                        onHover {
                            animate(0.08, Easings.QUINT_OUT) {
                                color = if (hovered) hoveredColor else normalColor
                                scale = V3(if (hovered) 1.1 else 1.0, if (hovered) 1.1 else 1.0, 1.0)
                            }
                        }
                        onMouseUp {
                            close()
                        }
                        +text {
                            align = CENTER
                            origin = CENTER
                            color = WHITE
                            scale = V3(0.9, 0.9, 0.9)
                            content = "Выйти [ ESC ]"
                            shadow = true
                        }
                    }
                })
                addChild()
                open()
            }
        }

        mod.registerHandler<KeyPress> {
            if (key == Keyboard.KEY_V) {
                clientApi.clientConnection().sendPayload("key_v", Unpooled.buffer())
            }
        }
    }

    private fun close() {
        opened = false
        context.close()
    }
}