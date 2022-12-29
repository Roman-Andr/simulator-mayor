package me.slavita.construction.action.command.menu.project

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.City
import me.slavita.construction.player.User
import me.slavita.construction.project.Project
import me.slavita.construction.structure.WorkerStructure
import me.slavita.construction.utils.user
import org.bukkit.entity.Player

class StartProject(val user: User, val project: Project, val structure: WorkerStructure) : CooldownCommand(user.player, 3 * 20) {
    override fun execute() {
        project.structure.playerCell.setBusy()
        (project.structure as WorkerStructure).workers.addAll(structure.workers)
        project.start()
        player.user.currentCity.addProject(project)
    }
}