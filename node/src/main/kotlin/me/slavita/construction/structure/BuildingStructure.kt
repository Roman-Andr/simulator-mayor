package me.slavita.construction.structure

import me.slavita.construction.player.User
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
	val cell: Cell,
) {
	protected var currentBlock: StructureBlock? = null
	protected var hidden = false
	private var currentProject: Project? = null
	private val visual = StructureVisual(this)
	var cityStructure: CityStructure? = null
	var state = StructureState.NOT_STARTED
	var blocksPlaced = 0
	val box = cell.box
	val allocation = box.min

	protected abstract fun enterBuilding()

	protected abstract fun blockPlaced()

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
	}

	fun placeCurrentBlock() {
		if (state != StructureState.BUILDING) return

		world.placeFakeBlock(owner.player, currentBlock!!.withOffset(allocation))
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
		cityStructure = owner.city.addStructure(CityStructure(owner.player, structure, cell))
	}

	fun claimed() {
		visual.hideFinish()
		state = StructureState.REWARD_CLAIMED
	}
}