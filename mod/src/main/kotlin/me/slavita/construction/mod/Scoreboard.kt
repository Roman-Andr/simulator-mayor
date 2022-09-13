package me.slavita.construction.mod

import dev.xdark.clientapi.event.lifecycle.GameLoop
import ru.cristalix.clientapi.registerHandler
import ru.cristalix.clientapi.KotlinMod
import ru.cristalix.uiengine.element.CarvedRectangle
import ru.cristalix.uiengine.element.TextElement
import ru.cristalix.uiengine.utility.*

object Scoreboard {

    var score: CarvedRectangle
    var show = true

    val contents: HashMap<String, TextElement> = mapOf(
        "gold" to text { lineHeight = 8.0 },
        "mobs" to text { lineHeight = 8.0 }
    ).onEach { (_, value) -> value.color = WHITE } as HashMap<String, TextElement>

    val contentHeight = contents.values.sumOf { it.lineHeight } + 58.0

    init {
        score = carved {
            origin = RIGHT
            align = RIGHT
            size = V3(115.0, contentHeight)
            color = Color(0, 0, 0, .62)
            carveSize = 2.0
            enabled = show
            +text {
                align = TOP
                origin = TOP
                color = WHITE
                content = "Стройка"
                offset.y += 4.0
            }
            +flex {
                offset.y += 25.0
                offset.x += 5.0
                flexSpacing = 3.0
                flexDirection = FlexDirection.DOWN
                contents.forEach { (_, value) ->
                    +value
                }
            }
            +text {
                content = "www.cristalix.gg"
                color = WHITE
                align = BOTTOM
                origin = BOTTOM
                offset.y -= 4.0
            }
        }

        registerHandler<GameLoop> { score.enabled = show }

        mod.registerChannel("scoreboard:hide") {
            show = false
        }

        mod.registerChannel("scoreboard:show") {
            show = true
        }
    }


}