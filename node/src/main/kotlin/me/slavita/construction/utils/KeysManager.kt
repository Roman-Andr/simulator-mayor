package me.slavita.construction.utils

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.ControlPanelMenu

object KeysManager {
    init {
        Anime.createReader("menu:open") { player, _ ->
            ControlPanelMenu(player).tryExecute()
        }
    }
}