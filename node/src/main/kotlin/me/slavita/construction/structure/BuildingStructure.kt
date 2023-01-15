package me.slavita.construction.structure

import me.slavita.construction.player.User
import me.slavita.construction.player.sound.Music.playSound
import me.slavita.construction.player.sound.MusicSound
import me.slavita.construction.project.FreelanceProject
import me.slavita.construction.project.Project
import me.slavita.construction.structure.instance.Structure
import me.slavita.construction.structure.tools.StructureState
import me.slavita.construction.structure.tools.StructureVisual
import me.slavita.construction.world.GameWorld
import me.slavita.construction.world.StructureBlock

abstract class BuildingStructure(
    val world: GameWorld,
    val structure: Structure,
    val owner: User,
    val cell: PlayerCell,
) {
    protected var currentBlock: StructureBlock? = null
    protected var hidden = false
    var currentProject: Project? = null
        private set
    var cityStructure: CityStructure? = null
    var state = StructureState.NOT_STARTED
    var blocksPlaced = 0
    val box = cell.box
    val allocation = box.min
    val visual = StructureVisual(this)

    protected abstract fun enterBuilding()

    protected abstract fun blockPlaced()

    protected abstract fun onFinish()

    protected abstract fun onShow()

    protected abstract fun onHide()

    abstract fun getBannerInfo(): List<Pair<String, Double>>

    fun showVisual() {
        hidden = false
        visual.show()
        onShow()
    }

    fun hideVisual() {
        hidden = true
        visual.hide()
        onHide()
    }

    fun deleteVisual() {
        hidden = true
        visual.delete()
        onHide()
    }

    fun startBuilding(project: Project) {
        state = StructureState.BUILDING
        currentBlock = structure.getFirstBlock()

        enterBuilding()
        currentProject = project
        visual.start()
        owner.updatePosition()
    }

    fun placeCurrentBlock() {
        if (state != StructureState.BUILDING) return
        owner.player.playSound(MusicSound.HINT)

        world.placeFakeBlock(owner.player, currentBlock!!.withOffset(allocation), currentProject!! !is FreelanceProject)
        currentBlock = structure.getNextBlock(currentBlock!!.position)
        blockPlaced()

        blocksPlaced++
        visual.update()
        if (currentBlock == null) {
            finishBuilding()
            return
        }
    }

    private fun finishBuilding() {
        state = StructureState.FINISHED
        deleteVisual()
        visual.finishShow()
        if (currentProject!! !is FreelanceProject) cityStructure =
            cell.city.addStructure(CityStructure(owner.player, structure, cell))
        onFinish()
        owner.updatePosition()
    }

    fun claimed() {
        visual.hideFinish()
        state = StructureState.REWARD_CLAIMED
    }
}
