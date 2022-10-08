package me.slavita.construction.utils

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.ControlPanelMenu
import me.slavita.construction.action.command.menu.worker.WorkerBuyMenu

object ModCallbacks {
    init {
        Anime.createReader("menu:open") { player, _ ->
            ControlPanelMenu(player).tryExecute()
        }

        Anime.createReader("lootbox:closed") { player, _ ->
            WorkerBuyMenu(player).tryExecute()
        }
    }
}