package me.slavita.construction.register

import me.func.mod.conversation.ModLoader
import me.slavita.construction.common.utils.IRegistrable

object ModLoader : IRegistrable {
    override fun register() {
        ModLoader.loadAll("mods")
        ModLoader.onJoining("construction-mod.jar")
    }
}
