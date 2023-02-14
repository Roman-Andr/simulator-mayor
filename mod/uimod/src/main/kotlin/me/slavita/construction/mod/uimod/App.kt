package me.slavita.construction.mod.uimod

import dev.xdark.clientapi.entity.EntityPlayerSP
import me.slavita.construction.common.utils.register
import me.slavita.construction.mod.uimod.bank.CreditTaking
import me.slavita.construction.mod.uimod.showcase.Showcases
import me.slavita.construction.mod.uimod.storage.Storage
import ru.cristalix.clientapi.KotlinMod
import ru.cristalix.uiengine.UIEngine

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
            CellBorders,
            WorldButton,
        )
    }
}
