package me.slavita.construction.player

import me.slavita.construction.app
import me.slavita.construction.project.Project
import me.slavita.construction.structure.CityStructure
import me.slavita.construction.structure.PlayerCell
import me.slavita.construction.structure.tools.CityStructureState
import me.slavita.construction.utils.PlayerExtensions.deny
import org.bukkit.Bukkit
import org.bukkit.ChatColor.*

class City(val owner: User, id: String, val title: String, val price: Long, var unlocked: Boolean) {
    val projects = hashSetOf<Project>()
    val cityStructures = hashSetOf<CityStructure>()
    val playerCells = hashSetOf<PlayerCell>()
    val box = app.mainWorld.map.box("city", id)

    init {
        app.mainWorld.cells.forEach {
            playerCells.add(PlayerCell(this, it, false))
        }
        Bukkit.server.scheduler.scheduleSyncRepeatingTask(app, {
            if (cityStructures.size == 0) return@scheduleSyncRepeatingTask
            cityStructures.shuffled().chunked(5)[0].forEach {
                if (it.state == CityStructureState.BROKEN || it.state == CityStructureState.NOT_READY) return@scheduleSyncRepeatingTask
                owner.income -= it.structure.income
                it.state = CityStructureState.BROKEN
                owner.player.deny(
                    """
                        ${RED}Поломка!
                        ${GRAY}Номер: ${GRAY}${it.playerCell.id}
                        ${AQUA}Название: ${GOLD}${it.structure.name}
                        ${GOLD}Локация: ${GREEN}$title
                    """.trimIndent()
                )
                it.visual.update()
            }
        }, 0L, 2 * 60 * 20L)
    }

    fun addStructure(cityStructure: CityStructure): CityStructure {
        cityStructures.add(cityStructure)
        return cityStructure
    }

    fun addProject(project: Project) {
        projects.add(project)
    }

    fun finishProject(project: Project) {
        projects.remove(project)
    }

    fun getSpawn() = box.getLabel("spawn")
}
