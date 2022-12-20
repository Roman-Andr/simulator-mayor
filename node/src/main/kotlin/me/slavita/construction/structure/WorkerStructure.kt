package me.slavita.construction.structure

import me.slavita.construction.player.User
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.utils.runAsync
import me.slavita.construction.worker.Worker
import me.slavita.construction.world.GameWorld

class WorkerStructure(
    world: GameWorld,
    structure: Structure,
    owner: User,
    playerCell: PlayerCell,
    val workers: HashSet<Worker> = hashSetOf(),
) : BuildingStructure(world, structure, owner, playerCell) {
    private val delayTime: Long
        get() {
            if (workers.isEmpty()) return 1
            return (60 / workers.sumOf { it.blocksSpeed }).toLong()
        }

    override fun enterBuilding() {
        build()
    }

    override fun getBannerInfo(): List<Pair<String, Double>> {
        return listOf(
            Pair("Информация об постройке", 0.7),
            Pair("Название: ${structure.name}", 0.7),
            Pair("Рабочих: ${workers.size}", 0.7),
            Pair("", 0.5),
            Pair("Скорость: ${workers.sumOf { it.blocksSpeed }} блоков в секунду", 0.5),
            Pair("Прогресс: $blocksPlaced из ${structure.blocksCount} блоков", 0.5)
        )
    }

    override fun onShow() {}

    override fun onHide() {}

    override fun blockPlaced() {}

    private fun build() {
        if (state != StructureState.BUILDING) return
        runAsync(delayTime) {
            if (workers.isNotEmpty()) {
                placeCurrentBlock()
            }
            build()
        }
    }
}
