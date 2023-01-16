package me.slavita.construction.action.chat

import me.func.atlas.Atlas
import me.func.mod.Anime
import me.slavita.construction.action.command.menu.achievements.AchievementsChoiceMenu
import me.slavita.construction.action.command.menu.city.CityHallMenu
import me.slavita.construction.action.command.menu.city.LocationsMenu
import me.slavita.construction.action.command.menu.donate.DonateMenu
import me.slavita.construction.action.command.menu.general.SettingsMenu
import me.slavita.construction.action.command.menu.general.TagsMenu
import me.slavita.construction.action.command.menu.project.ActiveProjectsMenu
import me.slavita.construction.action.command.menu.project.ChoiceStructureGroup
import me.slavita.construction.action.command.menu.project.StartFreelanceProject
import me.slavita.construction.action.command.menu.storage.StorageMenu
import me.slavita.construction.action.command.menu.worker.WorkerMenu
import me.slavita.construction.app
import me.slavita.construction.prepare.GuidePrepare
import me.slavita.construction.project.ProjectGenerator
import me.slavita.construction.utils.command
import me.slavita.construction.utils.opCommand
import me.slavita.construction.utils.user
import org.bukkit.entity.Player
import ru.cristalix.core.keyboard.IKeyService
import ru.cristalix.core.keyboard.Key

object UserCommands {
    init {
        command("dialog") { player, args ->
            if (args[0] != Atlas.find("dialogs").getString("command-key")) return@command

            GuidePrepare.tryNext(player.user)
        }

        command("break") { player, _ ->
            player.user.currentCity.breakStructure()
        }

        command("ok") { _, _ -> }


        /* Быстрый доступ */

        listen("projects", Key.K) { player ->
            ActiveProjectsMenu(player).tryExecute()
        }

        listen("locations", Key.L) { player, ->
            LocationsMenu(player).tryExecute()
        }

        listen("tags", null) { player, ->
            TagsMenu(player).tryExecute()
        }

        listen("achievements", null) { player, ->
            AchievementsChoiceMenu(player).tryExecute()
        }

        listen("settings", null) { player, ->
            SettingsMenu(player).tryExecute()
        }

        listen("workers", Key.M) { player, ->
            WorkerMenu(player).tryExecute()
        }

        listen("storage", null) { player, ->
            StorageMenu(player).tryExecute()
        }

        listen("cityhall", null) { player, ->
            CityHallMenu(player).tryExecute()
        }

        listen("donate", null) { player, ->
            DonateMenu(player).tryExecute()
        }

        listen("freelance", null) { player ->
            StartFreelanceProject(player)
        }
    }

    private fun listen(command: String, key: Key?, action: (player: Player) -> Unit) {
        command(command) { player, _ ->
            action(player)
        }
        if (key == null) return
        IKeyService.get().addListener(key) { player ->
            action(player)
        }
    }
}
