package me.slavita.construction.action.menu.project

import me.func.mod.Anime
import me.slavita.construction.action.command.CooldownCommand
import me.slavita.construction.app
import me.slavita.construction.city.project.ProjectGenerator
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class StartFreelanceProject(val player: Player) :
    CooldownCommand(player.user, 3 * 20) {
    override fun execute() {
        user.run {
            ChoiceStructureGroup(player) { structure ->
                player.teleport(app.mainWorld.freelanceCell.box.bottomCenter)
                currentFreelance = ProjectGenerator.generateFreelance(
                    this,
                    structure
                )
                Anime.close(player)
            }.keepHistory().tryExecute()
        }
    }
}
