package me.slavita.construction.action.command.menu.project

import me.func.mod.Anime
import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.app
import me.slavita.construction.player.User
import me.slavita.construction.project.Project
import me.slavita.construction.project.ProjectGenerator
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class StartFreelanceProject(val player: Player) :
    CooldownCommand(player.user, 3 * 20) {
    override fun execute() {
        user.run {
            player.teleport(app.mainWorld.freelanceCell.box.bottomCenter)
            ChoiceStructureGroup(player, freelanceCell) { structure ->
                currentFreelance = ProjectGenerator.generateFreelance(
                    this,
                    structure
                )
                Anime.close(player)
            }.tryExecute()
        }
    }
}