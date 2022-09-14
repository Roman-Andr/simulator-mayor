package me.slavita.construction.mod

import dev.xdark.clientapi.event.render.PlayerListRender
import dev.xdark.clientapi.event.render.RenderPass
import me.slavita.construction.mod.util.Renderer
import ru.cristalix.clientapi.KotlinMod
import ru.cristalix.uiengine.UIEngine
import ru.cristalix.uiengine.utility.*

lateinit var mod: App

class App : KotlinMod() {

    lateinit var cube: V3
    var inited = false
    var gameActive = false

    override fun onEnable() {
        mod = this
        UIEngine.initialize(this)
        UIEngine.overlayContext.addChild(Scoreboard.score)

        BlocksLeft
    }
}