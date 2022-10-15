package me.slavita.construction.utils

import me.func.mod.Anime
import me.slavita.construction.action.command.menu.project.BuildingControlMenu
import me.slavita.construction.action.command.menu.worker.WorkerBuyMenu
import me.slavita.construction.app

object ModCallbacks {
    init {
        Anime.createReader("menu:open") { player, _ ->
            //ControlPanelMenu(player).tryExecute()
            if (app.getUser(player).watchableProject != null) {
                BuildingControlMenu(player, app.getUser(player).watchableProject!!).tryExecute()
            }
        }

        /*Anime.createReader("lootbox:closed") { player, _ ->
            WorkerBuyMenu(player).tryExecute()
        }*/
    }
}