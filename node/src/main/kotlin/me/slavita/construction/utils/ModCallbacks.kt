package me.slavita.construction.utils

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.project.BuildingInfoMenu
import me.slavita.construction.action.command.menu.worker.WorkerBuyMenu
import me.slavita.construction.app

object ModCallbacks {
    init {
        Anime.createReader("menu:open") { player, _ ->
            //ControlPanelMenu(player).tryExecute()
            if (app.getUser(player).watchableProject != null) {
                BuildingInfoMenu(player, app.getUser(player).watchableProject!!).tryExecute()
            }
        }

        Anime.createReader("lootbox:closed") { player, _ ->
            WorkerBuyMenu(player).tryExecute()
        }
    }
}