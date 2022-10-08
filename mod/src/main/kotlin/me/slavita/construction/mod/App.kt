package me.slavita.construction.mod

import dev.xdark.clientapi.entity.EntityPlayerSP
import me.slavita.construction.mod.showcase.Showcases
import ru.cristalix.clientapi.KotlinMod
import ru.cristalix.uiengine.UIEngine
import java.util.*

lateinit var mod: App
lateinit var player: EntityPlayerSP

class App : KotlinMod() {

    override fun onEnable() {
        UIEngine.initialize(this)
        player = clientApi.minecraft().player
        mod = this
        StructureBuilding
        KeysManager
        Showcases
    }

    fun runRepeatingTask(delay: Double, period: Double, action: () -> Unit) {
        Timer().schedule(object: TimerTask() {
            override fun run() {
                action()
            }
        }, (delay * 1000).toLong(), (period * 1000).toLong())
    }
}