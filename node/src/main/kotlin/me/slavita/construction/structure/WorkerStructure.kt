package me.slavita.construction.structure

import me.func.mod.util.after
import me.slavita.construction.player.User
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.extensions.LoggerUtils.fine
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.GameWorld

class WorkerStructure(
    world: GameWorld,
    structure: Structure,
    owner: User,
    cell: Cell,
    val workers: HashSet<Worker> = hashSetOf()
) : BuildingStructure(world, structure, owner, cell) {
    private val delayTime: Long
        get() {
            if (workers.isEmpty()) return 1
            return (60 / workers.sumOf { it.blocksSpeed }).toLong()
        }

    override fun enterBuilding() {
        owner.player.fine(owner.city.projects.map{it.structure.state}.toString())
        build()
    }

    override fun onShow() { }

    override fun onHide() { }

    override fun blockPlaced() { }

    override fun getBannerInfo(): List<Pair<String, Double>> {
        return listOf(
            Pair("Информация об постройке", 0.7),
            Pair("Название: ${structure.name}", 0.7),
            Pair("Рабочих: ${workers.size}", 0.7),
            Pair("", 0.5),
            Pair("Скорость: ${workers.sumOf { it.blocksSpeed }} блоков в секунду", 0.5),
            Pair("Прогресс: $blocksPlaced блоков из ${structure.blocksCount}", 0.5)
        )
    }

    private fun build() {
        if (state != StructureState.BUILDING) return
        after(delayTime) {
            if (workers.isNotEmpty()) {
                placeCurrentBlock()
            }
            build()
        }
    }
}