package me.slavita.construction.mod

import dev.xdark.clientapi.entity.EntityPlayerSP
import me.slavita.construction.common.utils.*
import me.slavita.construction.mod.bank.CreditTaking
import me.slavita.construction.mod.showcase.Showcases
import me.slavita.construction.mod.storage.Storage
import ru.cristalix.clientapi.KotlinMod
import ru.cristalix.uiengine.UIEngine
import java.util.Timer
import java.util.TimerTask

lateinit var mod: App
lateinit var player: EntityPlayerSP

class App : KotlinMod() {

    override fun onEnable() {
        UIEngine.initialize(this)
        mod = this
        player = clientApi.minecraft().player

        LoadingScreen().show()

        register(
            StructureBuilding,
            Showcases,
            CreditTaking,
            Storage,
            CellBorders
        )
    }
}
