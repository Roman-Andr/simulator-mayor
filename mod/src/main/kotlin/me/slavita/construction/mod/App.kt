package me.slavita.construction.mod

import ru.cristalix.clientapi.KotlinMod
import ru.cristalix.uiengine.UIEngine

lateinit var mod: App

class App : KotlinMod() {
    override fun onEnable() {
        mod = this
        UIEngine.initialize(this)
        UIEngine.overlayContext.addChild(Scoreboard.score)
        BuildingInfo()
    }
}