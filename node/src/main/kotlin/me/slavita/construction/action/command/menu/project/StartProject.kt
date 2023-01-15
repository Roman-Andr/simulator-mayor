package me.slavita.construction.action.command.menu.project

import me.slavita.construction.action.CooldownCommand
import me.slavita.construction.player.User
import me.slavita.construction.project.Project
import me.slavita.construction.structure.WorkerStructure

class StartProject(override val user: User, val project: Project, val structure: WorkerStructure) :
    CooldownCommand(user, 3 * 20) {
    override fun execute() {
        project.structure.cell.setBusy()
        (project.structure as WorkerStructure).workers.addAll(structure.workers)
        project.start()
        user.currentCity.addProject(project)
    }
}